package com.example.codingbat.service;

import com.example.codingbat.entity.Member;
import com.example.codingbat.entity.Problem;
import com.example.codingbat.payload.MemberDto;
import com.example.codingbat.payload.Status;
import com.example.codingbat.repository.MemberRepository;
import com.example.codingbat.repository.ProblemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record MemberService (MemberRepository repository,
                             ProblemRepository problemRepository) {
    public List<Member> getAll() {
        return repository.findAll();
    }

    public Member getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Status add(MemberDto dto) {
        if (repository.existsByEmail(dto.getEmail()))
            return Status.builder()
                    .status(false)
                    .message("Email already exists")
                    .build();
        if(dto.getPassword().length() < 8)
            return Status.builder()
                    .status(false)
                    .message("Password must be at least 8 characters")
                    .build();
        if(!dto.getPassword().equals(dto.getPrePassword()))
            return Status.builder()
                    .status(false)
                    .message("Password and confirm password must be the same")
                    .build();
        repository.save(
                Member.builder()
                        .email(dto.getEmail())
                        .password(dto.getPassword())
                        .build()
        );
        return Status.builder()
                .status(true)
                .message("Successfully added")
                .build();
    }

    public Status edit(Long id, MemberDto dto) {
        Member member = repository.findById(id).orElse(null);
        if (member == null)
            return Status.builder()
                    .status(false)
                    .message("Member not found")
                    .build();
        if (repository.existsByEmailAndIdNot(dto.getEmail(), id))
            return Status.builder()
                    .status(false)
                    .message("Email already exists")
                    .build();
        if(dto.getPassword().length() < 8)
            return Status.builder()
                    .status(false)
                    .message("Password must be at least 8 characters")
                    .build();
        if(!dto.getPassword().equals(dto.getPrePassword()))
            return Status.builder()
                    .status(false)
                    .message("Password and confirm password must be the same")
                    .build();
        member.setEmail(dto.getEmail());
        member.setPassword(dto.getPassword());
        repository.save(member);
        return Status.builder()
                .status(true)
                .message("Successfully edited")
                .build();
    }

    public Status delete(Long id) {
        if (!repository.existsById(id))
            return Status.builder()
                    .status(false)
                    .message("Member not found")
                    .build();
        problemSolves(id);
        repository.deleteById(id);
        return Status.builder()
                .status(true)
                .message("Successfully deleted")
                .build();
    }

    private void problemSolves(Long id) {
        for (Problem problem : problemRepository.findAllByMembersId(id)) {
            problem.getMembers().remove(repository.getById(id));
        }
    }
}
