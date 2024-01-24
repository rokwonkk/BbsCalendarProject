package dto;

import java.io.Serializable;

// BBS Bulletin Board System
public class BbsDto implements Serializable {
    private int seq;    // sequence 글번호
    private String id;  //작성자

    /****** 답글용 ******/
    private int ref;
    private int step;
    private int depth;

    private String title;   //제목
    private String content; //내용 -> textarea -> summer note(그림 + 글)
    private String wdate;   //작성일
    private int del;        //삭제여부
    private int readcount;  //조회수

    public BbsDto() {
    }

    public BbsDto(String id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public BbsDto(int seq, String id, int ref, int step, int depth, String title, String content, String wdate, int del, int readcount) {
        this.seq = seq;
        this.id = id;
        this.ref = ref;
        this.step = step;
        this.depth = depth;
        this.title = title;
        this.content = content;
        this.wdate = wdate;
        this.del = del;
        this.readcount = readcount;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRef() {
        return ref;
    }

    public void setRef(int ref) {
        this.ref = ref;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWdate() {
        return wdate;
    }

    public void setWdate(String wdate) {
        this.wdate = wdate;
    }

    public int getDel() {
        return del;
    }

    public void setDel(int del) {
        this.del = del;
    }

    public int getReadcount() {
        return readcount;
    }

    public void setReadcount(int readcount) {
        this.readcount = readcount;
    }

    @Override
    public String toString() {
        return "BbsDto{" +
                "seq=" + seq +
                ", id='" + id + '\'' +
                ", ref=" + ref +
                ", step=" + step +
                ", depth=" + depth +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", wdate='" + wdate + '\'' +
                ", del=" + del +
                ", readcount=" + readcount +
                '}';
    }
}
