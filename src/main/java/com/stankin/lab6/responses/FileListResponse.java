package com.stankin.lab6.responses;

import com.stankin.lab6.models.File;

import java.util.ArrayList;
import java.util.List;

public class FileListResponse extends Response {
    public List<File> items;

    public FileListResponse () {
        this.items = new ArrayList<File>();
    }

    public FileListResponse (List<File> items) {
        this.items = items;
    }

    public Long getUserId(int index) {
        return items.get(index).getOwnerId();
    }
}
