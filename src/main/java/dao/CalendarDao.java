package dao;

import db.DBClose;
import db.DBConnection;
import dto.CalendarDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CalendarDao {

    private static final CalendarDao dao = new CalendarDao();

    private CalendarDao() {
    }

    public static CalendarDao getInstance() {
        return dao;
    }

    public List<CalendarDto> getCalendarList(String id, String yyyyMM) {

        String sql = "select seq,id,title, content, rdate, wdate from "
                + "    ( select row_number() over (partition by substr(rdate, 1 ,8) order by rdate asc) as rnum, "
                + "    seq, id, title, content, rdate, wdate from calendar "
                + "    where id=? and substr(rdate, 1 , 6)=? ) a "
                + " where rnum between 1 and 5 ";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<CalendarDto> list = new ArrayList<>();
        try {
            conn = DBConnection.getConnection();
            System.out.println("CalendarDao.getCalendarList 1");


            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, id);
            pstmt.setString(2, yyyyMM);
            System.out.println("CalendarDao.getCalendarList 2");

            rs = pstmt.executeQuery();

            while (rs.next()) {

                CalendarDto dto = new CalendarDto(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)
                );
                list.add(dto);
            }

            System.out.println("CalendarDao.getCalendarList 3");

        } catch (SQLException e) {
            System.out.println("CalendarDao.getCalendarList fail");
            throw new RuntimeException(e);
        } finally {
            DBClose.close(conn, pstmt, rs);
        }

        return list;
    }

    public boolean addCalendar(CalendarDto dto) {

        String sql = "insert into calendar(id, title, content, rdate, wdate) values"
                + "(?,?,?,?,now()) ";

        Connection conn = null;
        PreparedStatement pstmt = null;

        int count = 0;

        try {
            conn = DBConnection.getConnection();
            System.out.println("CalendarDao.addCalendar 1");

            pstmt = conn.prepareStatement(sql);
            System.out.println("CalendarDao.addCalendar 2");

            pstmt.setString(1, dto.getId());
            pstmt.setString(2, dto.getTitle());
            pstmt.setString(3, dto.getContent());
            pstmt.setString(4, dto.getRdate());

            count = pstmt.executeUpdate();
            System.out.println("CalendarDao.addCalendar 3");

        } catch (SQLException e) {
            System.out.println("CalendarDao.addCalendar fail");
            throw new RuntimeException(e);
        } finally {
            DBClose.close(conn, pstmt, null);
        }
        return count > 0;
    }

    public CalendarDto getCalendar(int seq) {

        String sql = "select seq, id, title, content, rdate, wdate from calendar "
                + "where seq = ? ";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        CalendarDto cal = null;

        try {
            conn = DBConnection.getConnection();

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, seq);

            rs = pstmt.executeQuery();

            if (rs.next()) {

                int seq1 = rs.getInt(1);            //seq
                String id = rs.getString(2);        //id
                String title = rs.getString(3);     //title
                String content = rs.getString(4);   //content
                String rdate = rs.getString(5);     //rdate
                String wdate = rs.getString(6);     //wdate

                cal = new CalendarDto(seq1, id, title, content, rdate, wdate);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBClose.close(conn, pstmt, rs);
        }
        return cal;
    }

    public boolean calDelete(int seq) {
        String sql = "delete from calendar "
                + "where seq = ? ";

        Connection conn = null;
        PreparedStatement pstmt = null;

        int count = 0;

        try {

            conn = DBConnection.getConnection();
            System.out.println("CalendarDao.calendarDelete 1/3 success");

            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, seq);
            System.out.println("CalendarDao.calendarDelete 2/3 success");

            count = pstmt.executeUpdate();
            System.out.println("CalendarDao.calendarDelete 3/3 success");
        } catch (SQLException e) {
            System.out.println("CalendarDao.calendarDelete fail");
            throw new RuntimeException(e);
        } finally {
            DBClose.close(conn, pstmt,null);
        }
        return count > 0 ? true : false;
    }

    public boolean calDetailUpdate(int seq, String title, String content) {
        String sql = "update calendar "
                + "set title = ? , content = ? "
                + "where seq = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;

        int count = 0;

        try {

            conn = DBConnection.getConnection();
            System.out.println("CalendarDao.calDetailUpdate 1/3 success");

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, title);
            pstmt.setString(2, content);
            pstmt.setInt(3, seq);
            System.out.println("CalendarDao.calDetailUpdate 2/3 success");

            count = pstmt.executeUpdate();
            System.out.println("CalendarDao.calDetailUpdate 3/3 success");
        } catch (SQLException e) {
            System.out.println("CalendarDao.calDetailUpdate fail");
            throw new RuntimeException(e);
        } finally {
            DBClose.close(conn, pstmt,null);
        }
        return count > 0 ? true : false;
    }

    public List<CalendarDto> calDayList(String id, String yyyymmdd){
        String sql = "select seq, id, title, content, rdate, wdate "
                + "from calendar "
                + "where id=? and substr(rdate, 1, 8)=? "
                + "order by rdate asc ";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<CalendarDto> list = new ArrayList<>();

        try {
            conn = DBConnection.getConnection();
            System.out.println("CalendarDao.calDayList 1/3 success");

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, id);
            pstmt.setString(2, yyyymmdd);

            rs = pstmt.executeQuery();
            System.out.println("CalendarDao.calDayList 2/3 success");

            if (rs.next()) {

                CalendarDto dto = new CalendarDto(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)
                );
                list.add(dto);
            }
            System.out.println("CalendarDao.calDayList 3/3 success");
        } catch (SQLException e) {
            System.out.println("CalendarDao.calDayList fail");
            throw new RuntimeException(e);
        } finally {
            DBClose.close(conn, pstmt, rs);
        }
        return list;
    }

    public boolean updateCalendar(CalendarDto dto) {

        String sql = " update calendar "
                + "    set title=?, content=?, rdate=?, wdate=now() "
                + "    where seq=? ";

        Connection conn = null;
        PreparedStatement psmt = null;
        int count = 0;

        try {
            conn = DBConnection.getConnection();
            System.out.println("1/3 updateCalendar success");

            psmt = conn.prepareStatement(sql);
            psmt.setString(1, dto.getTitle());
            psmt.setString(2, dto.getContent());
            psmt.setString(3, dto.getRdate());
            psmt.setInt(4, dto.getSeq());
            System.out.println("2/3 updateCalendar success");

            count = psmt.executeUpdate();
            System.out.println("3/3 updateCalendar success");

        } catch (SQLException e) {
            System.out.println("updateCalendar fail");
            e.printStackTrace();
        } finally {
            DBClose.close(conn, psmt, null);
        }

        return count > 0;
    }

    public boolean deleteCalendar(int seq) {
        String sql = " delete from calendar "
                + "    where seq=? ";

        Connection conn = null;
        PreparedStatement psmt = null;
        int count = 0;

        try {
            conn = DBConnection.getConnection();
            System.out.println("1/3 deleteCalendar success");

            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, seq);
            System.out.println("2/3 deleteCalendar success");

            count = psmt.executeUpdate();
            System.out.println("3/3 deleteCalendar success");

        } catch (SQLException e) {
            System.out.println("deleteCalendar fail");
            e.printStackTrace();
        } finally {
            DBClose.close(conn, psmt, null);
        }

        return count > 0;
    }
}