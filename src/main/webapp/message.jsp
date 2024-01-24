<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // 회원가입
    String message = (String) request.getAttribute("message");
    if (message != null && !message.isEmpty()) {
        if (message.equals("MEMBER_YES")) {
%>
<script type="text/javascript">
    alert("성공적으로 가입되었습니다");
    location.href = "./member?param=login";
</script>
<%
} else {
%>
<script type="text/javascript">
    alert("다시 가입해 주십시오");
    location.href = "./member?param=regi";
</script>
<%
        }
    }
// 로그인
    String loginMsg = (String) request.getAttribute("loginMsg");
    if (loginMsg != null && !loginMsg.isEmpty()) {
        if (loginMsg.equals("LOGIN_SUCCESS")) {
            String prevView = (String) session.getAttribute("prevView");
            System.out.println("prevView:" + prevView);
            //글작성시에 로그인 검사
            if (prevView.equals("bbsWrite")) {
%>
<script type="text/javascript">
    alert("로그인되었습니다");
    location.href = "./bbs?param=" + "<%=prevView%>";
    <%--location.href = "./bbs?param=" + "<%=prevView%>" + "&seq=" + "<%=seq%>";--%>
    // location.href = "./bbs?param=bbsWrite";
</script>
<%
    //글 보기 시에 로그인 검사.
} else if (prevView.equals("bbsDetail")) {
    int seq = (Integer) session.getAttribute("seq");
%>
<script type="text/javascript">
    alert("로그인되었습니다");
    location.href = "./bbs?param=" + "<%=prevView%>" + "&seq=" + "<%=seq%>";
</script>
<%
} else if (prevView.equals("calendarList")) {
%>
<script type="text/javascript">
    alert("로그인되었습니다");
    location.href = "./calendar?param=" + "<%=prevView%>";
</script>
<%
    }
} else {
%>
<script type="text/javascript">
    alert("아이디나 패스워드를 확인해 주세요");
    location.href = "./member?param=login";
</script>
<%
        }
    }

// 글쓰기
    String writeMsg = (String) request.getAttribute("writeMsg");
    if (writeMsg != null && !writeMsg.isEmpty()) {
        if (writeMsg.equals("WRITE_SUCCESS")) {
%>
<script>
    alert("성공적으로 추가되었습니다.");
    location.href = "./bbs?param=bbsList";
</script>
<% } else { %>
<script>
    alert("다시 작성해 주십시오");
    location.href = "./bbs?param=bbsWrite";
</script>
<%
        }
    }
    String bbsDetailUpdateMsg = (String) request.getAttribute("bbsDetailUpdateMsg");
    if (bbsDetailUpdateMsg != null && !bbsDetailUpdateMsg.isEmpty()) {
        if (bbsDetailUpdateMsg.equals("UPDATE_SUCCESS")) {
%>
<script>
    alert("글수정에 성공하였습니다.");
    location.href = "./bbs?param=bbsList";
</script>
<% } else { %>
<script>
    alert("글수정에 실패하였습니다.");
    location.href = "./bbs?param=bbsList";
</script>
<%
        }
    }
    String bbsDeleteMsg = (String) request.getAttribute("bbsDeleteMsg");
    if (bbsDeleteMsg != null && !bbsDeleteMsg.isEmpty()) {
        if (bbsDeleteMsg.equals("DELETE_SUCCESS")) {
%>
<script>
    alert("글삭제에 성공하였습니다.");
    location.href = "./bbs?param=bbsList";
</script>
<% } else { %>
<script>
    alert("글삭제에 실패하였습니다.");
    location.href = "./bbs?param=bbsList";
</script>
<%
        }
    }
    String bbsAnswerMsg = (String) request.getAttribute("bbsAnswerMsg");
    if (bbsAnswerMsg != null && !bbsAnswerMsg.isEmpty()) {
        if (bbsAnswerMsg.equals("ANSWER_SUCCESS")) {
%>
<script>
    alert("답글입력 성공~!");
    location.href = "./bbs?param=bbslist";
</script>
<%
} else {
%>
<script>
    alert("답글입력 실패~!");
    location.href = "./bbs?param=bbslist";
</script>
<%
        }
    }

    String calWrite = (String) request.getAttribute("calWrite");
    if (calWrite != null && !calWrite.isEmpty()) {
        if (calWrite.equals("CAL_WRITE_OK")) {
%>
<script type="text/javascript">
    alert("성공적으로 추가되었습니다");
    location.href = "calendar?param=calendarList";
</script>
<%
} else {
%>
<script type="text/javascript">
    alert("추가되지 않았습니다");
    location.href = "calendar?param=calendarList";
</script>
<%
        }
    }
    String calUpdate = (String) request.getAttribute("calUpdate");
    if (calUpdate != null && !calUpdate.equals("")) {
        if (calUpdate.equals("CAL_UPDATE_OK")) {
%>
<script type="text/javascript">
    alert("성공적으로 수정되었습니다");
    location.href = "calendar?param=calendarList";
</script>
<%
} else {
%>
<script type="text/javascript">
    alert("수정되지 않았습니다");
    location.href = "calendar?param=calendarList";
</script>
<%
        }
    }
    String calDelete = (String) request.getAttribute("calDelete");
    if (calDelete != null && !calDelete.equals("")) {
        if (calDelete.equals("CAL_DELETE_OK")) {
%>
<script type="text/javascript">
    alert("성공적으로 삭제되었습니다");
    location.href = "calendar?param=calendarList";
</script>
<%
} else {
%>
<script type="text/javascript">
    alert("삭제되지 않았습니다");
    location.href = "calendar?param=calendarList";
</script>
<%
        }
    }
    String calendarList = (String) request.getAttribute("calendarList");
    if (calendarList != null && !calendarList.isEmpty()) {
%>
<script type="text/javascript">
    alert("로그인 해 주십시오");
    location.href = "./member?param=login";
</script>
<%
    }
%>