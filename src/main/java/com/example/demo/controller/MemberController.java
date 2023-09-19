package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.Member;
import com.example.demo.service.MemberService;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/all")
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = memberService.getAllMembers();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable String id) {
        Optional<Member> member = memberService.getMemberById(id);
        return member.map(value -> ResponseEntity.ok(value))
                .orElse(ResponseEntity.notFound().build());
    }
    @RestController
    @RequestMapping("/api/images")
    public class ImageController {

        @GetMapping("/{imageName:.+}")
        public ResponseEntity<Resource> getImage(@PathVariable String imageName) throws IOException {
            // Load the image from the resources directory
            Resource imageResource = (Resource) new ClassPathResource("static/images/" + imageName);

            // Return the image with appropriate content type
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // Adjust content type as needed
                    .body(imageResource);
        }
    }

    @PostMapping
    public ResponseEntity<Member> createMember(@ModelAttribute Member member, @RequestParam("image") MultipartFile image) {
        try {
            if (image != null && !image.isEmpty()) {
                // Save the image and get the filename
                String imageName = memberService.saveImage(image);

                // Set the image filename in the member
                member.setImageName(imageName);
            }

            // Save the member with the updated image filename
            Member createdMember = memberService.createMember(member);
            return new ResponseEntity<>(createdMember, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PutMapping("/edit/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable String id, @RequestBody Member member) throws ChangeSetPersister.NotFoundException {
        Member updatedMember = memberService.updateMember(id, member);
        return new ResponseEntity<>(updatedMember, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable String id) {
        memberService.deleteMember(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAllMembers() {
        memberService.deleteAllMembers();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
