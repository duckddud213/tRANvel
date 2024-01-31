package com.ssafy.tranvel.controller;


import com.ssafy.tranvel.dto.AnnouncementDto;
import com.ssafy.tranvel.dto.ResponseDto;
import com.ssafy.tranvel.entity.Announcement;
import com.ssafy.tranvel.service.AnnouncementService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Getter @Setter
@RequiredArgsConstructor
@RestController
@RequestMapping("/announcement")
public class AnnounceController {

    private ResponseDto response;
    private final AnnouncementService announcementService;


    @GetMapping("/search")
    public ResponseEntity<ResponseDto> getAnnouncement() {
        List<Announcement> announcementList = announcementService.getAllAnnouncement();
        if (announcementList.isEmpty()) {
            response = new ResponseDto(false, "작성된 공지사항이 없습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        response = new ResponseDto(true, "공지사항 전체 조회");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> postAnnouncement(@RequestBody @Validated AnnouncementDto announcementDto){
        announcementService.createAnnouncement(announcementDto);

        response = new ResponseDto(true, "공지사항이 등록되었습니다.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
