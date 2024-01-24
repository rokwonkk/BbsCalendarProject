package controller;

import dao.MemberDao;
import dto.MemberDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;

import java.io.IOException;

public class MemberController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProp(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProp(req, resp);
    }

    public void doProp(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        String param = req.getParameter("param");

        MemberDao dao = MemberDao.getInstance();

        if (param.equals("home")) {
            res.sendRedirect("main.jsp");

        } else if (param.equals("login")) {
            req.setAttribute("main", "login");
            req.getRequestDispatcher("main.jsp").forward(req, res);

        } else if (param.equals("regi")) {
            res.sendRedirect("regi.jsp");

        } else if (param.equals("idcheck")) { //ajax
            String id = req.getParameter("id");

            int count = dao.idcheck(id);

            String str = "NO";
            if (count == 0) {
                str = "YES";
            }

            JSONObject obj = new JSONObject(); //ajax로 보낼때
            obj.put("str", str);    //짐싸

            res.setContentType("application/x-json;charset=utf-8");
            res.getWriter().print(obj); //보낸다.
        } else if (param.equals("regiAf")) { // 회원가입
//            System.out.println("회원가입");

            String id = req.getParameter("id");
            String pw = req.getParameter("pw");
            String name = req.getParameter("name");
            String email = req.getParameter("email");

            boolean b = dao.addMember(new MemberDto(id, pw, name, email, 0));
            String message = "";
            if (b) {
                message = "MEMBER_YES";
            } else {
                message = "MEMBER_NO";
            }
            req.setAttribute("message", message); //짐싸

            //이동 잘가
            req.getRequestDispatcher("message.jsp").forward(req, res);

        } else if (param.equals("loginAf")) {
            System.out.println("로그인");

            //id, pw 취득
            String id = req.getParameter("id");
            String pw = req.getParameter("pw");
            System.out.println(id + " " + pw);

            //db 접근
            MemberDto login = dao.login(id, pw);

            String loginMsg = "LOGIN_SUCCESS";
            if (login == null) {
                loginMsg = "LOGIN_FAIL";
            } else {
                req.getSession().setAttribute("login", login);
                req.getSession().setMaxInactiveInterval(24 * 60 * 60);
            }
            req.setAttribute("loginMsg", loginMsg); // 짐싸

            req.getRequestDispatcher("message.jsp").forward(req, res); //잘가
        }
    }
}
