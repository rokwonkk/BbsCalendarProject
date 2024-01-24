package controller;

import dao.BbsDao;
import dto.BbsDto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/bbs") // annotation
public class BbsController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProc(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProc(req, resp);
    }

    public void doProc(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        String param = req.getParameter("param");

        BbsDao dao = BbsDao.getInstance();

        if (param.equals("bbsList")) {

            String choice = req.getParameter("choice");
            String search = req.getParameter("search");
            if (choice == null) {
                choice = "";
            }
            if (search == null) {
                search = "";
            }

            // 페이지 넘버
            String sPageNumber = req.getParameter("pageNumber");
            int pageNumber = 0;
            if (sPageNumber != null && !sPageNumber.isEmpty()) {
                pageNumber = Integer.parseInt(sPageNumber);
            }

            // 게시판 목록
            List<BbsDto> list = dao.getBbsPageList(choice, search, pageNumber);

            // 글의 총수
            int count = dao.getAllBbs(choice, search);

            // 페이지(수)를 계산	14 -> 2페이지		32 -> 4페이지
            int pageBbs = count / 10;
            if ((count % 10) > 0) {
                pageBbs = pageBbs + 1;
            }

            // 짐싸!
            req.setAttribute("list", list);
            req.setAttribute("pageBbs", pageBbs);
            req.setAttribute("pageNumber", pageNumber);
            req.setAttribute("choice", choice);
            req.setAttribute("search", search);

            // 잘가
//            req.getRequestDispatcher("bbsList.jsp").forward(req, res);

            req.setAttribute("main", "bbsList");
            req.getRequestDispatcher("main.jsp").forward(req, res);

        } else if (param.equals("bbsWrite")) {

            req.setAttribute("main", "bbsWrite");
            req.getRequestDispatcher("main.jsp").forward(req, res);

        } else if (param.equals("bbsWriteAf")) {

            String id = req.getParameter("id");
            String title = req.getParameter("title");
            String content = req.getParameter("content");

            boolean b = dao.bbsWrite(new BbsDto(id, title, content));

            String writeMsg = "WRITE_SUCCESS";
            if (!b) {
                writeMsg = "WRITE_FAIL";
            }
            req.setAttribute("writeMsg", writeMsg); // 짐싸

//            req.getRequestDispatcher("message.jsp").forward(req, res); //잘가

            forward("message.jsp", req, res);
        } else if (param.equals("bbsDetail")) {

            int seq = Integer.parseInt(req.getParameter("seq"));

            //접속한 이력을 조사 !참고!
            //조회수 증가
            dao.readcount(seq);

            BbsDto dto = dao.getBbs(seq);
            System.out.println(dto.toString());

            // 짐싸!
            req.setAttribute("dto", dto);

            req.setAttribute("main", "bbsDetail");
            req.getRequestDispatcher("main.jsp").forward(req, res);
        } else if (param.equals("bbsupdate")) {

            int seq = Integer.parseInt(req.getParameter("seq"));

            BbsDto dto = dao.getBbs(seq);

            req.setAttribute("seq", dto.getSeq());
            req.setAttribute("id", dto.getId());
            req.setAttribute("wdate", dto.getWdate());
            req.setAttribute("content", dto.getContent());
            req.setAttribute("title", dto.getTitle());
            req.setAttribute("readcount", dto.getReadcount());

            req.getRequestDispatcher("bbsDetailUpdate.jsp").forward(req, res);
        } else if (param.equals("bbsDetailUpdateAf")) {

            int seq = Integer.parseInt(req.getParameter("seq"));
            String title = req.getParameter("title");
            String content = req.getParameter("content");

            System.out.println(seq);
            System.out.println(title + "  " + content);

            boolean b = dao.bbsDetailUpdate(seq, title, content);

            String bbsDetailUpdateMsg = "UPDATE_SUCCESS";
            if (!b) {
                bbsDetailUpdateMsg = "UPDATE_FAIL";
            }

            req.setAttribute("bbsDetailUpdateMsg", bbsDetailUpdateMsg);

            forward("message.jsp", req, res);
        } else if (param.equals("bbsDelete")) {

            int seq = Integer.parseInt(req.getParameter("seq"));

            boolean b = dao.bbsDelite(seq);

            String bbsDeleteMsg = "DELETE_SUCCESS";
            if (!b) {
                bbsDeleteMsg = "DELETE_FAIL";
            }

            req.setAttribute("bbsDeleteMsg", bbsDeleteMsg);
            forward("message.jsp", req, res);
        } else if (param.equals("answer")) {
            int seq = Integer.parseInt(req.getParameter("seq"));

            BbsDto dto = dao.getBbs(seq);       //기본글

            req.setAttribute("dto", dto);

            req.getRequestDispatcher("answer.jsp").forward(req, res);
        } else if (param.equals("answerAf")) {
            int seq = Integer.parseInt(req.getParameter("seq"));
            String id = req.getParameter("id");
            String title = req.getParameter("title");
            String content = req.getParameter("content");

            dao.answerUpdate(seq);

            boolean b = dao.answerInsert(seq, new BbsDto(id, title, content));

            String bbsAnswerMsg = "ANSWER_SUCCESS";
            if (!b) {
                bbsAnswerMsg = "ANSWER_FAIL";
            }

            req.setAttribute("bbsAnswerMsg", bbsAnswerMsg);

            req.getRequestDispatcher("message.jsp").forward(req, res);
        }
    }

    public void forward(String link, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.getRequestDispatcher(link).forward(req,resp);

        RequestDispatcher rd = req.getRequestDispatcher(link);
        rd.forward(req, resp);
    }
}
