<%@ page import="dto.CalendarDto" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="util.CalendarUtil" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<CalendarDto> list = (List<CalendarDto>) request.getAttribute("list");

    String pp = (String) request.getAttribute("pp");
    String p = (String) request.getAttribute("p");
    String n = (String) request.getAttribute("n");
    String nn = (String) request.getAttribute("nn");

    int year = (Integer) request.getAttribute("year");
    int month = (Integer) request.getAttribute("month");
    int dayOfWeek = (Integer) request.getAttribute("dayOfWeek");

    Calendar cal = (Calendar) request.getAttribute("cal");
%>
<html>
<head>
    <title>Title</title>
    <style>
        td {
            border: 1px solid #D3D3D3FF;
        }
    </style>
</head>
<body>

<br/>

<div align="center">
    <table>
        <col width="120"/>
        <col width="120"/>
        <col width="120"/>
        <col width="120"/>
        <col width="120"/>
        <col width="120"/>
        <col width="120"/>

        <tr height="80">
            <td colspan="7" align="center">
                <%=pp%>&nbsp;<%=p%>&nbsp;&nbsp;&nbsp;&nbsp;

                <font style="color: #3c3c3c; font-size: 40px; font-family: fantasy; vertical-align: middle">
                    <%=String.format("%d년&nbsp;&nbsp;%2d월", year, month)%>
                </font>

                &nbsp;&nbsp;&nbsp;&nbsp;<%=n%>&nbsp;<%=nn%>
            </td>
        </tr>

        <tr height="30" style="background-color: lightblue; color: white;">
            <th class="text-center">일</th>
            <th class="text-center">월</th>
            <th class="text-center">화</th>
            <th class="text-center">수</th>
            <th class="text-center">목</th>
            <th class="text-center">금</th>
            <th class="text-center">토</th>
        </tr>
        <tr height="120" align="left" valign="top">
            <%
                //윗쪽 빈칸
                for (int i = 1; i < dayOfWeek; i++) {
            %>
            <td style="background-color: #eeeeee">&nbsp;</td>
            <%
                }

                //날짜
                int lastday = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                for (int i = 1; i <= lastday; i++) {
            %>
            <td style="color: #3c3c3c; padding-top: 5px">
                <%=CalendarUtil.daylist(year, month, i)%>&nbsp;&nbsp;<%=CalendarUtil.calWrite(year, month, i)%>
                <%=CalendarUtil.makeTable(year, month, i, list)%>
            </td>
            <%
                if ((i + dayOfWeek - 1) % 7 == 0 && i != lastday) {
            %>
        </tr>
        <tr height="120" align="left" valign="top">
            <%
                    }
                }

                // 아래쪽 빈칸
                cal.set(Calendar.DATE, lastday);
                int weekday = cal.get(Calendar.DAY_OF_WEEK);
                for (int i = 0; i < 7 - weekday; i++) {
            %>
            <td style="background-color: #eeeeee">&nbsp;</td>
            <%
                }
            %>
        </tr>
    </table>
</div>

</body>
</html>