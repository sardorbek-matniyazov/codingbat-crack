package com.example.codingbat.service;

import com.example.codingbat.entity.Problem;
import com.example.codingbat.entity.ProblemTest;
import com.example.codingbat.payload.ProblemDto;
import com.example.codingbat.payload.SolveDto;
import com.example.codingbat.payload.Status;
import com.example.codingbat.repository.MemberRepository;
import com.example.codingbat.repository.ProblemGroupRepository;
import com.example.codingbat.repository.ProblemRepository;
import com.example.codingbat.repository.ProblemTestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public record ProblemService(ProblemRepository repository,
                             ProblemGroupRepository groupRepository,
                             ProblemTestRepository testRepository,
                             MemberRepository memberRepository) {
    public List<Problem> getAll() {
        return repository.findAll();
    }

    public Problem getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Status add(ProblemDto dto) {
        if(repository.existsByName(dto.getName()))
            return Status.builder()
                    .message("Problem with name " + dto.getName() + " already exists")
                    .status(false)
                    .build();
        if(!groupRepository.existsById(dto.getGroupId()))
            return Status.builder()
                    .status(false)
                    .message("Problem group with id " + dto.getGroupId() + " does not exist")
                    .build();

        Problem problem = new Problem(
                dto.getName(),
                dto.getDescription(),
                dto.getKey(),
                groupRepository.getById(
                        dto.getGroupId()
                ),
                dto.getDifficulty()
        );
        problem = repository.save(problem);
        saveTests(problem, dto.getTests());
        return Status.builder()
                .status(true)
                .message("Problem added")
                .build();

    }

    private void saveTests(Problem problem, List<ProblemTest> tests) {
        tests.forEach(test -> {
            test.setProblem(problem);
            testRepository.save(test);
        });
    }

    public Status edit(Long id, ProblemDto dto) {
        Optional<Problem> byId = repository.findById(id);
        if(byId.isEmpty())
            return Status.builder()
                    .message("Problem with id " + id + " does not exist")
                    .status(false)
                    .build();
        if(repository.existsByNameAndIdNot(dto.getName(), id))
            return Status.builder()
                    .message("Problem with name " + dto.getName() + " already exists")
                    .status(false)
                    .build();
        if(!groupRepository.existsById(dto.getGroupId()))
            return Status.builder()
                    .status(false)
                    .message("Problem group with id " + dto.getGroupId() + " does not exist")
                    .build();
        Problem problem = byId.get();
        problem.setName(dto.getName());
        problem.setDescription(dto.getDescription());
        problem.setKey(dto.getKey());
        problem.setProblemGroup(groupRepository.getById(dto.getGroupId()));
        problem.setDifficulty(dto.getDifficulty());
        problem = repository.save(problem);

        saveTests(problem, dto.getTests());
        return Status.builder()
                .status(true)
                .message("Problem edited")
                .build();
    }

    public Status delete(Long id) {
        if(!repository.existsById(id))
            return Status.builder()
                    .message("Problem with id " + id + " does not exist")
                    .status(false)
                    .build();
        deleteTestCases(id);
        repository.deleteById(id);
        return Status.builder()
                .status(true)
                .message("Problem deleted")
                .build();
    }

    private void deleteTestCases(Long id) {
        testRepository.deleteAllByProblemId(id);
    }

    public Status solve(Long id, SolveDto dto) {
        if(!repository.existsById(id))
            return Status.builder()
                    .message("Problem with id " + id + " does not exist")
                    .status(false)
                    .build();
        if (!memberRepository.existsById(dto.getMemberId()))
            return Status.builder()
                    .message("Member with id " + id + " does not exist")
                    .status(false)
                    .build();
        // TODO: check if member has solved this problem
        Problem problem = repository.getById(id);
        setMember(problem, dto.getMemberId());
        return Status.builder()
                .status(true)
                .message("Correct")
                .build();
    }

    private void setMember(Problem problem, Long memberId) {
        problem.getMembers().add(memberRepository.getById(memberId));
    }

}
