package com.konai.hsyang.konatoy.file.dto;

import com.konai.hsyang.konatoy.file.domain.File;
import lombok.Getter;

@Getter
public class FileResponseDto {

    private Long fileID;
    private Long postID;
    private String orgFileName;
    private Long fileSize;

    public FileResponseDto(File entity) {
        this.fileID = entity.getFileID();
        this.postID = entity.getPosts().getPostsID();
        this.orgFileName = entity.getOrgfilename();
        this.fileSize = entity.getFilesize();
    }
}
