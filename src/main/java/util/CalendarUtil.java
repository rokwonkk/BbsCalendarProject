package util;

import dto.CalendarDto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

public class CalendarUtil {

    //문자열 검사 : 빈문자열 -> true
    public static boolean nvl(String msg) {
        //msg 널이거나 빈문자면 true 반환
        return msg == null || msg.trim().isEmpty();
    }

    //한자리 숫자를 두자리로 만들어 주는 함수 : 1 ~ 9 -> 01 ~ 09
    public static String two(String msg) {
        return msg.trim().length() < 2 ? "0" + msg.trim() : msg.trim();
    }

    //앞에 (숫자)0이 있는 문자열을 한자리 문자로 만들어 주는 함수
    public static String one(String msg) {
        return msg.charAt(0) == '0' ? msg.charAt(1) + "" : msg;
    }

    //일정 추가
    public static String calWrite(int year, int month, int day) {
        String str = "";

        String img = "<img src='./images/pen2.png' width='18px' height=18px title='일정추가'>";
        str = String.format("<a href='./calendar?param=calWrite&year=%d&month=%d&day=%d'>%s</a>",
                year, month, day, img);
        return str;
    }

    //일별 일정을 모두 볼 수 있는 caldaylist.jsp로 이동되도록 하는 함수
    public static String daylist(int year, int month, int day) {
        String str = "";

        str += String.format("&nbsp;<a href='calDayList.jsp?year=%d&month=%d&day=%d' style='color:black'>",
                year, month, day);
        str += String.format("%2d", day);
        str += "</b></a>";

        return str;
    }

    //제목이 길 경우에 ...으로 처리하는 함수
    public static String dot3(String msg) {
        String str = "";

        if (msg.length() >= 10) {
            str = msg.substring(0, 10);
            str += "...";
        } else {
            str = msg.trim();
        }
        return str;
    }

    //날짜별로 테이블을 생성하기 위한 함수
    public static String makeTable(int year, int month, int day, List<CalendarDto> list) {
        String str = "";

        // 202401180415
        String dates = (year + "") + two(month + "") + two(day + "");

        str += "<table>";

        for(CalendarDto dto : list) {
            if(dto.getRdate().substring(0, 8).equals(dates)) {
                str += "<tr>";
                str += "	<td style='padding:0px'>";
                str += "		<a href='calDetail.jsp?seq=" + dto.getSeq() + "'>";
                str += "			<font style='font-size:15px;color:blue'>";
                str += 					dot3( dto.getTitle() );
                str += "			</font>";
                str += "		</a>";
                str += "	</td>";
                str += "</tr>";
            }
        }
        str += "</table>";

        return str;
    }

    //날짜와 시간을 사용자가 보기 쉽게 출력하는 함수
    //202401181622 -> 2024년 01월 18일 16시 22분
    public static String toDates(String mdate) {

        //날짜형식
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분");

        String s = mdate.substring(0, 4) + "-"                  //yyyy
                + mdate.substring(4, 6) + "-"                   //MM
                + mdate.substring(6, 8) + " "                   //dd
                + mdate.substring(8, 10) + ":"                  //hh
                + mdate.substring(10) + ":00";      //mm

        Timestamp d = Timestamp.valueOf(s);
        return sdf.format(d);
    }
}
