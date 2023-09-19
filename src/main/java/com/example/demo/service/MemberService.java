package com.example.demo.service;
import com.example.demo.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import com.example.demo.repository.MemberRepository;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> getMemberById(String id) {
        return memberRepository.findById(id);
    }

    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    public Member updateMember(String id, Member member) throws ChangeSetPersister.NotFoundException {
        Optional<Member> existingMember = memberRepository.findById(id);

        if (existingMember.isPresent()) {
            // Update fields of the existingMember based on member's properties
            Member updatedMember = existingMember.get();
            updatedMember.setFirstName(member.getFirstName());
            updatedMember.setLastName(member.getLastName());
            // Update other fields...

            return memberRepository.save(updatedMember);
        } else {
            throw new ChangeSetPersister.NotFoundException();
        }
    }
    @Value("${image.upload.directory}")
    private String uploadDirectory;

    public String saveImage(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path filePath = Paths.get(uploadDirectory, fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }

    public void deleteMember(String id) {
        memberRepository.deleteById(id);
    }

    public void deleteAllMembers() {
        memberRepository.deleteAll();
    }

    // Add other service methods as needed
}
