package dao;

import db.DBClose;
import db.DBConnection;
import dto.BbsDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BbsDao {

    private static final BbsDao dao = new BbsDao();

    private BbsDao() {
    }

    public static BbsDao getInstance() {
        return dao;
    }

    public List<BbsDto> getBbsList() {
        String sql = "select seq, id, ref, step, depth, "
                + " title, content, wdate, del, readcount "
                + " from bbs "
                + " order by ref desc, step asc ";

        Connection conn = null;
        System.out.println("BbsDao.getBbsList 1/4 success");

        PreparedStatement pstmt = null;
        System.out.println("BbsDao.getBbsList 2/4 success");

        ResultSet rs = null;
        System.out.println("BbsDao.getBbsList 3/4 success");

        List<BbsDto> list = new ArrayList<>();

        try {
            conn = DBConnection.getConnection();

            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            while (rs.next()) {

//                int seq = rs.getInt("seq");
//                String id = rs.getString("id");
//
//                int ref = rs.getInt("ref");
//                int step = rs.getInt("step");
//                int depth = rs.getInt("depth");
//
//                String title = rs.getString("title");
//                String content = rs.getString("content");
//                String wdate = rs.getString("wdate");
//
//                int del = rs.getInt("del");
//                int readcount = rs.getInt("readcount");
//
//                BbsDto dto = new BbsDto(seq, id, ref, step, depth, title, content, wdate, del, readcount);

                int i = 1;
                BbsDto dto = new BbsDto(
                        rs.getInt(i++),       //seq
                        rs.getString(i++),    //id
                        rs.getInt(i++),       //ref
                        rs.getInt(i++),       //step
                        rs.getInt(i++),       //depth
                        rs.getString(i++),    //title
                        rs.getString(i++),    //content
                        rs.getString(i++),    //wdate
                        rs.getInt(i++),       //del
                        rs.getInt(i));         //readcount
                list.add(dto);
            }
            System.out.println("BbsDao.getBbsList 4/4 success");


        } catch (SQLException e) {
            System.out.println("BbsDao.getBbsList fail");
            throw new RuntimeException(e);
        } finally {
            DBClose.close(conn, pstmt, rs);
        }
        return list;
    }

    public List<BbsDto> getBbsSearchList(String choice, String search) {
        String sql = "select seq, id, ref, step, depth, "
                + " title, content, wdate, del, readcount "
                + " from bbs ";
        //검색
        if (choice.equals("title")) {
            sql += " where title like '%" + search + "%' ";
        } else if (choice.equals("content")) {
            sql += " where content like '%" + search + "%' ";
        } else if (choice.equals("writer")) {
            sql += " where id = '" + search + "' ";
        }
        sql += " order by ref desc, step asc ";

        Connection conn = null;
        System.out.println("BbsDao.getBbsList 1/4 success");

        PreparedStatement pstmt = null;
        System.out.println("BbsDao.getBbsList 2/4 success");

        ResultSet rs = null;
        System.out.println("BbsDao.getBbsList 3/4 success");

        List<BbsDto> list = new ArrayList<>();

        try {
            conn = DBConnection.getConnection();

            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            while (rs.next()) {

//                int seq = rs.getInt("seq");
//                String id = rs.getString("id");
//
//                int ref = rs.getInt("ref");
//                int step = rs.getInt("step");
//                int depth = rs.getInt("depth");
//
//                String title = rs.getString("title");
//                String content = rs.getString("content");
//                String wdate = rs.getString("wdate");
//
//                int del = rs.getInt("del");
//                int readcount = rs.getInt("readcount");
//
//                BbsDto dto = new BbsDto(seq, id, ref, step, depth, title, content, wdate, del, readcount);

                int i = 1;
                BbsDto dto = new BbsDto(
                        rs.getInt(i++),       //seq
                        rs.getString(i++),    //id
                        rs.getInt(i++),       //ref
                        rs.getInt(i++),       //step
                        rs.getInt(i++),       //depth
                        rs.getString(i++),    //title
                        rs.getString(i++),    //content
                        rs.getString(i++),    //wdate
                        rs.getInt(i++),       //del
                        rs.getInt(i));         //readcount
                list.add(dto);
            }
            System.out.println("BbsDao.getBbsList 4/4 success");


        } catch (SQLException e) {
            System.out.println("BbsDao.getBbsList fail");
            throw new RuntimeException(e);
        } finally {
            DBClose.close(conn, pstmt, rs);
        }
        return list;
    }

    public boolean bbsWrite(BbsDto dto) {
        String sql = "insert into bbs(id, ref, step, depth, title, content, wdate, del, readcount) "
                + " values(?, (select ifnull(max(ref), 0) + 1 from bbs b), 0, 0, ?, ?, now(), 0, 0 )";

        Connection conn = null;
        PreparedStatement pstmt = null;

        int count = 0;

        try {
            conn = DBConnection.getConnection();
            System.out.println("bbsWrite 1/3 success");

            pstmt = conn.prepareStatement(sql);
            System.out.println("bbsWrite 2/3 success");

            pstmt.setString(1, dto.getId());
            pstmt.setString(2, dto.getTitle());
            pstmt.setString(3, dto.getContent());

            count = pstmt.executeUpdate();
            System.out.println("bbsWrite 3/3 success");

        } catch (SQLException e) {
            System.out.println("BbsDao.bbsWrite fail");
            throw new RuntimeException(e);
        } finally {
            DBClose.close(conn, pstmt, null);
        }
        return count > 0 ? true : false;
    }

    //글의 총수
    public int getAllBbs(String choice, String search) {
        String sql = "select COUNT(*) from bbs ";
        //검색
        switch (choice) {
            case "title":
                sql += " where title like '%" + search + "%' ";
                break;
            case "content":
                sql += " where content like '%" + search + "%' ";
                break;
            case "writer":
                sql += " where id = '" + search + "' ";
                break;
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int count = 0;

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBClose.close(conn, pstmt, rs);
        }
        return count;
    }

    //글 목록
    public List<BbsDto> getBbsPageList(String choice, String search, int pageNumber) {

        String sql = "select seq, id, ref, step, depth, title, content, wdate, del, readcount from bbs ";

        if (choice.equals("title")) {
            sql += " where title like '%" + search + "%' ";
        } else if (choice.equals("content")) {
            sql += " where content like '%" + search + "%' ";
        } else if (choice.equals("writer")) {
            sql += " where id = '" + search + "' ";
        }

        sql += "order by ref desc, step asc ";
        sql += "limit " + (pageNumber * 10) + ", 10 ";

        System.out.println(sql);

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<BbsDto> list = new ArrayList<>();
        try {
            conn = DBConnection.getConnection();
            System.out.println("BbsDao.getBbsPageList 1/4 success");

            pstmt = conn.prepareStatement(sql);
            System.out.println("BbsDao.getBbsPageList 2/4 success");

            rs = pstmt.executeQuery();
            System.out.println("BbsDao.getBbsPageList 3/4 success");
            while (rs.next()) {
                int i = 1;
                BbsDto dto = new BbsDto(
                        rs.getInt(i++),       //seq
                        rs.getString(i++),    //id
                        rs.getInt(i++),       //ref
                        rs.getInt(i++),       //step
                        rs.getInt(i++),       //depth
                        rs.getString(i++),    //title
                        rs.getString(i++),    //content
                        rs.getString(i++),    //wdate
                        rs.getInt(i++),       //del
                        rs.getInt(i));         //readcount
                list.add(dto);
            }
            System.out.println("BbsDao.getBbsPageList 4/4 success");

        } catch (SQLException e) {
            System.out.println("BbsDao.getBbsPageList fail");
            throw new RuntimeException(e);
        } finally {
            DBClose.close(conn, pstmt, rs);
        }
        return list;
    }

    /****************************************************************************************************************/
    /*
    public List<BbsDto> getBbsDetail(String seq) {

        String sql = "select seq, id, ref, step, depth, title, content, wdate, del, readcount from bbs "
                    + "where ref = " + seq;

        System.out.println(sql);

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<BbsDto> list = new ArrayList<>();
        try {
            conn = DBConnection.getConnection();
            System.out.println("BbsDao.getBbsDetail 1/4 success");

            pstmt = conn.prepareStatement(sql);
            System.out.println("BbsDao.getBbsDetail 2/4 success");

            rs = pstmt.executeQuery();
            System.out.println("BbsDao.getBbsDetail 3/4 success");

            if (rs.next()){ // 데이터가 있는 경우
                        int seq1 = rs.getInt(1);       //seq
                        String id = rs.getString(2);    //id
                        int ref = rs.getInt(3);       //ref
                        int step = rs.getInt(4);       //step
                        int depth = rs.getInt(5);       //depth
                        String title = rs.getString(6);    //title
                        String content = rs.getString(7);    //content
                        String wdate = rs.getString(8);    //wdate
                        int del = rs.getInt(9);       //del
                        int readcount = rs.getInt(10);         //readcount
                BbsDto dto = new BbsDto(seq1, id, ref, step, depth, title, content, wdate, del, readcount);

                list.add(dto);
            }
            System.out.println("BbsDao.getBbsDetail 4/4 success");

        } catch (SQLException e) {
            System.out.println("BbsDao.getBbsPageList fail");
            throw new RuntimeException(e);
        } finally {
            DBClose.close(conn, pstmt, rs);
        }
        return list;
    }
    /****************************************************************************************************************/
    public BbsDto getBbs(int seq) {

        String sql = "select seq, id, ref, step, depth, title, content, wdate, del, readcount from bbs "
                + "where seq = ? ";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        BbsDto bbs = null;

        try {
            conn = DBConnection.getConnection();

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, seq);

            rs = pstmt.executeQuery();

            if (rs.next()) {

                int seq1 = rs.getInt(1);       //seq
                String id = rs.getString(2);    //id
                int ref = rs.getInt(3);       //ref
                int step = rs.getInt(4);       //step
                int depth = rs.getInt(5);       //depth
                String title = rs.getString(6);    //title
                String content = rs.getString(7);    //content
                String wdate = rs.getString(8);    //wdate
                int del = rs.getInt(9);       //del
                int readcount = rs.getInt(10);         //readcount

                bbs = new BbsDto(seq1, id, ref, step, depth, title, content, wdate, del, readcount);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBClose.close(conn, pstmt, rs);
        }
        return bbs;
    }

    public void readcount(int seq) {
        String sql = "update bbs "
                + "set readcount=readcount+1 "
                + "where seq=?";

        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, seq);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBClose.close(conn, pstmt, null);
        }
    }

    public void answerUpdate(int seq) {
        String sql = "update bbs "
                + "set step = step + 1 "
                + "where ref=(select ref from (select ref from bbs a where seq=?) A) "
                + "and step>(select step from (select step from bbs b where seq=?) B) ";

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, seq);
            pstmt.setInt(2, seq);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBClose.close(conn, pstmt, null);
        }
    }

    public boolean answerInsert(int seq, BbsDto bbs) {

        String sql = "insert into bbs (id, ref, step, depth, title, content, wdate, del, readcount) "
                + "         values (?, "
                + "        (select ref from bbs a where seq=?), "
                + "        (select step from bbs b where seq=?) + 1, "
                + "        (select depth from bbs c where seq=?) + 1, "
                + "        ?, ?, now(), 0, 0) ";

        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;

        int count = 0;

        try {
            conn = DBConnection.getConnection();
            System.out.println("BbsDao.answerInsert 1/3 success");

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, bbs.getId());
            pstmt.setInt(2, seq);
            pstmt.setInt(3, seq);
            pstmt.setInt(4, seq);
            pstmt.setString(5, bbs.getTitle());
            pstmt.setString(6, bbs.getContent());
            System.out.println("BbsDao.answerInsert 2/3 success");

            count = pstmt.executeUpdate();
            System.out.println("BbsDao.answerInsert 3/3 success");


        } catch (SQLException e) {
            System.out.println("BbsDao.answerInsert fail");

            throw new RuntimeException(e);
        } finally {
            DBClose.close(conn, pstmt, null);
        }

        return count > 0 ? true : false;
    }

    public boolean bbsDetailUpdate(int seq, String title, String content) {
        String sql = "update bbs "
                + "set title = ? , content = ? "
                + "where seq = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;

        int count = 0;

        try {

            conn = DBConnection.getConnection();
            System.out.println("BbsDao.bbsDetailUpdate 1/3 success");

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, title);
            pstmt.setString(2, content);
            pstmt.setInt(3, seq);
            System.out.println("BbsDao.bbsDetailUpdate 2/3 success");

            count = pstmt.executeUpdate();
            System.out.println("BbsDao.bbsDetailUpdate 3/3 success");
        } catch (SQLException e) {
            System.out.println("BbsDao.bbsDetailUpdate fail");
            throw new RuntimeException(e);
        } finally {
            DBClose.close(conn, pstmt,null);
        }
        return count > 0 ? true : false;
    }

    public boolean bbsDelite(int seq) {
        String sql = "update bbs "
                + "set del = 1 "
                + "where seq = ? ";

        Connection conn = null;
        PreparedStatement pstmt = null;

        int count = 0;

        try {

            conn = DBConnection.getConnection();
            System.out.println("BbsDao.bbsDelite 1/3 success");

            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, seq);
            System.out.println("BbsDao.bbsDelite 2/3 success");

            count = pstmt.executeUpdate();
            System.out.println("BbsDao.bbsDelite 3/3 success");
        } catch (SQLException e) {
            System.out.println("BbsDao.bbsDelite fail");
            throw new RuntimeException(e);
        } finally {
            DBClose.close(conn, pstmt,null);
        }
        return count > 0 ? true : false;
    }
}
