package com.pavan.hireflow.job.service;

import com.pavan.hireflow.auth.entity.User;
import com.pavan.hireflow.candidate.entity.Skill;
import com.pavan.hireflow.candidate.repository.SkillRepository;
import com.pavan.hireflow.common.exception.JobAccessDeniedException;
import com.pavan.hireflow.common.exception.JobNotFoundException;
import com.pavan.hireflow.common.exception.RecruiterProfileNotFoundException;
import com.pavan.hireflow.company.entity.RecruiterProfile;
import com.pavan.hireflow.company.repository.RecruiterProfileRepository;
import com.pavan.hireflow.job.dto.JobRequest;
import com.pavan.hireflow.job.dto.JobResponse;
import com.pavan.hireflow.job.entity.Job;
import com.pavan.hireflow.job.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class JobService {

    private final JobRepository jobRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final SkillRepository  skillRepository;

    public JobService(JobRepository jobRepository, RecruiterProfileRepository recruiterProfileRepository, SkillRepository skillRepository) {
        this.jobRepository = jobRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.skillRepository = skillRepository;
    }


    public JobResponse createJob(User user, JobRequest request){

        RecruiterProfile recruiterProfile = recruiterProfileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RecruiterProfileNotFoundException("Recruiter Profile Not Found"));


        Job job = new Job();

        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setLocation(request.getLocation());
        job.setEmploymentType(request.getEmploymentType());
        job.setMinSalary(request.getMinSalary());
        job.setMaxSalary(request.getMaxSalary());

        job.setRecruiter(recruiterProfile);
        job.setCompany(recruiterProfile.getCompany());


        Set<Skill>  skills = new HashSet<>();

        for(String skillName : request.getRequiredSkills()){

            String normalizedSkillName = skillName.trim();

            Skill skill = skillRepository.findByNameIgnoreCase(normalizedSkillName)
                    .orElseGet(() -> {
                        Skill newSkill = new Skill();
                        newSkill.setName(normalizedSkillName);
                        return skillRepository.save(newSkill);
                    });

            skills.add(skill);
        }

        job.setRequiredSkills(skills);

        Job savedJob = jobRepository.save(job);

        return mapToResponse(savedJob);

    }

    public JobResponse getJob(Long jobId){

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new JobNotFoundException("Job not found"));

        return mapToResponse(job);

    }

    public List<JobResponse> getAllJobs(){

        List<Job> jobs = jobRepository.findAll();

        return jobs.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public JobResponse updateJob(Long jobId, User user, JobRequest request){

        Job job =  jobRepository.findById(jobId)
                .orElseThrow(() -> new JobNotFoundException("Job not found"));

        RecruiterProfile recruiterProfile = recruiterProfileRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RecruiterProfileNotFoundException("Recruiter Profile Not Found"));


        System.out.println("Recruiter Id: " + job.getRecruiter().getId());

        System.out.println("Recruiter Profile Id: " + recruiterProfile.getId());

        if(!job.getRecruiter().getId().equals(recruiterProfile.getId())){
            throw new JobAccessDeniedException("You are not allowed to update this job");
        }

        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setLocation(request.getLocation());
        job.setEmploymentType(request.getEmploymentType());
        job.setMinSalary(request.getMinSalary());
        job.setMaxSalary(request.getMaxSalary());

        Set<Skill> skills = new HashSet<>();

        for(String skillName : request.getRequiredSkills()){

            String normalizedSkillName = skillName.trim();

            Skill skill = skillRepository.findByNameIgnoreCase(normalizedSkillName)
                    .orElseGet(() -> {
                        Skill newSkill = new Skill();
                        newSkill.setName(normalizedSkillName);
                        return skillRepository.save(newSkill);
                    });
            skills.add(skill);
        }
        job.setRequiredSkills(skills);

        Job updatedJob = jobRepository.save(job);
        return mapToResponse(updatedJob);
    }

    private JobResponse mapToResponse(Job job){

        String recruiterName =
                job.getRecruiter().getUser().getFirstName()+" "+
                        job.getRecruiter().getUser().getLastName();

        Set<String> requiredSkills = job.getRequiredSkills()
                .stream()
                .map(Skill::getName)
                .collect(Collectors.toSet());

        return new JobResponse(
                job.getId(),
                job.getTitle(),
                job.getDescription(),
                job.getLocation(),
                job.getEmploymentType(),
                job.getMinSalary(),
                job.getMaxSalary(),
                job.getCompany().getId(),
                job.getCompany().getName(),
                job.getRecruiter().getId(),
                recruiterName,
                requiredSkills
        );
    }
}
