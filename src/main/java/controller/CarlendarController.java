package controller;

import dao.CalendarDao;
import dto.CalendarDto;
import dto.MemberDto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.CalendarUtil;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

@WebServlet("/calendar")
public class CarlendarController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPooc(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPooc(req, resp);
    }

    public void doPooc(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");

        String param = req.getParameter("param");

        CalendarDao dao = CalendarDao.getInstance();

        if (param.equals("calendarList")) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DATE, 1);

            String syear = req.getParameter("year");
            String smonth = req.getParameter("month");

            //현재 연도와 월을 구한다 -> 처음 이 페이지가 실행시에 적용
            int year = cal.get(Calendar.YEAR);
            if (!CalendarUtil.nvl(syear)) { //넘어 온 파라미터 값이 있음.
                year = Integer.parseInt(syear);
            }
            int month = cal.get(Calendar.MONTH) + 1;
            if (!CalendarUtil.nvl(smonth)) {
                month = Integer.parseInt(smonth);
            }

            if (month < 1) {
                month = 12;
                year--;
            }
            if (month > 12) {
                month = 1;
                year++;
            }

            cal.set(year, month - 1, 1);

            //요일
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

            // <<   year--
            String pp = String.format("<a href='./calendar?param=calendarList&year=%d&month=%d' style='text-decoration:none'>" +
                    "<img src='./images/left.png' width='20px' height='20px'>" +
                    "</a>", year - 1, month);

            // <    month--
            String p = String.format("<a href='./calendar?param=calendarList&year=%d&month=%d' style='text-decoration:none'>" +
                    "<img src='./images/prec.png' width='20px' height='20px'>" +
                    "</a>", year, month - 1);

            // >    month++
            String n = String.format("<a href='./calendar?param=calendarList&year=%d&month=%d' style='text-decoration:none'>" +
                    "<img src='./images/next.png' width='20px' height='20px'>" +
                    "</a>", year, month + 1);

            // >>   year++
            String nn = String.format("<a href='./calendar?param=calendarList&year=%d&month=%d' style='text-decoration:none'>" +
                    "<img src='./images/last.png' width='20px' height='20px'>" +
                    "</a>", year + 1, month);

            MemberDto login = (MemberDto) req.getSession().getAttribute("login");
            if(login == null || login.getId().isEmpty()) {
                req.setAttribute("calendarList", "LOGIN");
                req.getSession().setAttribute("prevView", "calendarList");

                forward("message.jsp", req, resp);
            } else {
                // 2023 01
                List<CalendarDto> list = dao.getCalendarList(login.getId(), year + CalendarUtil.two(month + ""));

                //짐싸
                req.setAttribute("list", list);
                req.setAttribute("pp", pp);
                req.setAttribute("p", p);
                req.setAttribute("nn", nn);
                req.setAttribute("n", n);
                req.setAttribute("year", year);
                req.setAttribute("month", month);
                req.setAttribute("cal", cal);
                req.setAttribute("dayOfWeek", dayOfWeek);

                //잘가
//                forward("calendarList.jsp", req, resp);
                req.setAttribute("main", "calendarList");
                forward("main.jsp", req, resp);
            }
        } else if (param.equals("calWrite")) {

            String year = req.getParameter("year");
            String month = req.getParameter("month");
            String day = req.getParameter("day");

            //1자리였을때 앞에 0추가 2024-1-3 -> 2024-01-03
            month = CalendarUtil.two(month);
            day = CalendarUtil.two(day);

            System.out.println(year + " " + month + " " + day);

            req.setAttribute("year", year);
            req.setAttribute("month", month);
            req.setAttribute("day", day);

            forward("calWrite.jsp", req, resp);
        } else if (param.equals("calWriteAf")) {
            String id = req.getParameter("id");
            String title = req.getParameter("title");
            String content = req.getParameter("content");

            String date = req.getParameter("date");
            String time = req.getParameter("time");
            date = date.replace("-", "");
            time = time.replace(":", "");

            String rdate = date + time;  // 202307141149

            System.out.println(rdate);

            boolean b = dao.addCalendar(new CalendarDto(id, title, content, rdate));
            String calWrite = "CAL_WRITE_OK";
            if (!b) {
                calWrite = "CAL_WRITE_NO";
            }
            req.setAttribute("calWrite", calWrite);
            forward("message.jsp", req, resp);
        } else if (param.equals("calDetail")) {
            int seq = Integer.parseInt(req.getParameter("seq"));

            CalendarDto dto = dao.getCalendar(seq);

            req.setAttribute("dto", dto);
            forward("calDetail.jsp", req, resp);
        } else if (param.equals("calDayList")) {
            String year = req.getParameter("year");
            String month = req.getParameter("month");
            String day = req.getParameter("day");

            // 20230710
            String yyyymmdd = year + CalendarUtil.two(month + "") + CalendarUtil.two(day + "");

            String id = ((MemberDto) req.getSession().getAttribute("login")).getId();

            List<CalendarDto> list = dao.calDayList(id, yyyymmdd);

            req.setAttribute("year", year);
            req.setAttribute("month", month);
            req.setAttribute("day", day);
            req.setAttribute("list", list);
            forward("calDayList.jsp", req, resp);
        } else if (param.equals("calUpdate")) {
            int seq = Integer.parseInt(req.getParameter("seq"));

            CalendarDto dto = dao.getCalendar(seq);

            // date
            String year = dto.getRdate().substring(0, 4);
            String month = dto.getRdate().substring(4, 6);
            String day = dto.getRdate().substring(6, 8);

            String date = year + "-" + month + "-" + day;

            // time
            String hour = dto.getRdate().substring(8, 10);
            String min = dto.getRdate().substring(10);

            String time = hour + ":" + min;

            req.setAttribute("date", date);
            req.setAttribute("time", time);
            req.setAttribute("dto", dto);
            forward("calUpdate.jsp", req, resp);
        } else if (param.equals("calUpdateAf")) {
            int seq = Integer.parseInt(req.getParameter("seq"));
            String title = req.getParameter("title");
            String content = req.getParameter("content");

            String date = req.getParameter("date");
            String time = req.getParameter("time");

            String datesplit[] = date.split("-");
            String timesplit[] = time.split(":");

            String rdate = datesplit[0] + datesplit[1] + datesplit[2] + timesplit[0] + timesplit[1];

            boolean isS = dao.updateCalendar(new CalendarDto(seq, null, title, content, rdate, null));
            String calUpdate = "CAL_UPDATE_OK";
            if (!isS) {
                calUpdate = "CAL_UPDATE_NO";
            }
            req.setAttribute("calUpdate", calUpdate);
            forward("message.jsp", req, resp);
        } else if (param.equals("calDelete")) {
            int seq = Integer.parseInt(req.getParameter("seq"));

            boolean isS = dao.deleteCalendar(seq);
            String calDelete = "CAL_DELETE_OK";
            if (!isS) {
                calDelete = "CAL_DELETE_NO";
            }
            req.setAttribute("calDelete", calDelete);
            forward("message.jsp", req, resp);
        }
    }

    public void forward(String link, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher(link);
        rd.forward(req, resp);
    }
}
