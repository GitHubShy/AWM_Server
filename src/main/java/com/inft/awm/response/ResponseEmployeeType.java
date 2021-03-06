package com.inft.awm.response;
/**
 * Response class for different employee type
 *
 * @author Yao Shi
 * @version 1.0
 * @date 2020/10/15 14:33 pm
 */
public class ResponseEmployeeType {
    private Integer id;
    private String name;
    private String title;

    public ResponseEmployeeType(Integer id, String name, String title) {
        this.id = id;
        this.name = name;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
