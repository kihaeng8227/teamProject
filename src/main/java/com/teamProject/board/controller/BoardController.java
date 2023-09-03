package com.teamProject.board.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.teamProject.board.model.*;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;

@WebServlet("*.do")
public class BoardController extends HttpServlet {
    static final int LISTCOUNT = 10;
    private String boardName = "board";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String RequestURI = req.getRequestURI();
        String contextPath = req.getContextPath();
        String command = RequestURI.substring(contextPath.length());

        resp.setContentType("text/html; charset=utf-8");
        req.setCharacterEncoding("utf-8");

        System.out.println(command);

        if (command.contains("/BoardListAction.do")) { // 등록된 글 목록 페이지 출력하기

            requestBoardList(req);
            RequestDispatcher rd = req.getRequestDispatcher("/board/list.jsp");
            rd.forward(req, resp);

        } else if (command.contains("/BoardWriteForm.do")) { // 글 등록 페이지 출력하기
//			requestLoginName(req);
            RequestDispatcher rd = req.getRequestDispatcher("/board/writeForm.jsp");
            rd.forward(req, resp);
        } else if (command.contains("/BoardWriteAction.do")) { // 새로운 글 등록하기
            try {
                requestBoardWrite(req);
            } catch (Exception e) {
                e.printStackTrace();
            }
            RequestDispatcher rd = req.getRequestDispatcher("/board/BoardListAction.do");
            rd.forward(req, resp);
        } else if (command.contains("/BoardViewAction.do")) { // 선택된 글 상세 페이지 가져오기
            requestBoardView(req);
            // requestRippleList(req);
            RequestDispatcher rd = req.getRequestDispatcher("/board/BoardView.do");
            rd.forward(req, resp);
        } else if (command.contains("/BoardView.do")) { // 글 상세 페이지 출력하기
            RequestDispatcher rd = req.getRequestDispatcher("/board/view.jsp");
            rd.forward(req, resp);
        } else if (command.contains("/BoardUpdateForm.do")) { // 글 수정페이지 출력
            requestBoardView(req);
            RequestDispatcher rd = req.getRequestDispatcher("/board/updateForm.jsp");
            rd.forward(req, resp);
        } else if (command.contains("/BoardUpdateAction.do")) { //이미지 삭제
            try {
                requestBoardUpdate(req);
            } catch (Exception e) {
                e.printStackTrace();
            }
            RequestDispatcher rd = req.getRequestDispatcher("/board/BoardListAction.do");
            rd.forward(req, resp);
        } else if (command.contains("/BoardDeleteAction.do")) { // 선택된 글 삭제하기
            requestBoardDelete(req);
            RequestDispatcher rd = req.getRequestDispatcher("/board/BoardListAction.do");
            rd.forward(req, resp);
        } else if (command.contains("RippleDeleteAction.do")) { // 댓글 삭제하기
            requestRippleDelete(req, resp);
        } else if (command.contains("RippleWriteAction.do")) { // 댓글 등록하기
            requestRippleWrite(req, resp);
        } else if (command.contains("RippleListAction.do")) { // 댓글 목록 출력
            requestRippleList(req, resp);
        } else if (command.contains("ReportAction.do")) { //신고하기
            requestReportWrite(req, resp);
        } else {
            System.out.println("out: " + command);
            // 결과 화면을 출력 스트림을 통해 출력
            PrintWriter out = resp.getWriter();
            out.append("<html><body><h2>잘못된 경로 입니다.(" + command + "</h2><hr>");
        }

    }

    //등록된 글 가져오기
    private void requestBoardList(HttpServletRequest req) {

        BoardDAO dao = BoardDAO.getInstance();
        List<BoardDTO> boardlist = new ArrayList<BoardDTO>();

        int pageNum = 1; // 페이지 번호가 전달이 안되면 1페이지.
        int limit = LISTCOUNT; // 페이지당 게시물 수.

        if (req.getParameter("pageNum") != null) // 페이지 번호가 전달이 된 경우.
            pageNum = Integer.parseInt(req.getParameter("pageNum"));

        String items = req.getParameter("items"); // 검색 필드.
        String text = req.getParameter("text"); // 검색어.

        int total_record = dao.getListCount(items, text); // 전체 게시물 수.
        boardlist = dao.getBoardList(pageNum, limit, items, text); // 현재 페이지에 해당하는 목록 데이터 가져오기.

        int total_page; // 전체 페이지

        if (total_record % limit == 0) { // 전체 게시물이 limit의 배수일 때.
            total_page = total_record / limit;
            Math.floor(total_page);
        } else {
            total_page = total_record / limit;
            Math.floor(total_page);
            total_page = total_page + 1;
        }
        req.setAttribute("limit", limit);
        req.setAttribute("pageNum", pageNum); // 페이지 번호.
        req.setAttribute("total_page", total_page); // 전체 페이지수
        req.setAttribute("total_record", total_record); // 전체 게시물 수.
        req.setAttribute("boardlist", boardlist);// 현재 페이지에 해당하는 목록 데이터.
        req.setAttribute("text", text); //검색
        req.setAttribute("items", items); //검색
    }

    // 인증된 사용자명 가져오기
//	public void requestLoginName(HttpServletRequest req) {
//
//		String id = req.getParameter("id");
//
//		BoardDAO dao = BoardDAO.getInstance();
//
//		String name = dao.getLoginNameById(id);
//
//		req.setAttribute("name", name);
//		System.out.println(name);
//	}
    // 새로운 글 등록하기
    public void requestBoardWrite(HttpServletRequest req) throws Exception {

        BoardDAO dao = BoardDAO.getInstance();
        BoardDTO board = new BoardDTO();


        // 현재 날짜()
        LocalDateTime now = LocalDateTime.now();
        String nowStr = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        HttpSession session = req.getSession();
        board.setId((String) session.getAttribute("sessionId"));

        // 폼 페이지에서 전송된 파일을 저장할 서버의 경로를 작성.
        String path = "C://imgs";

        // 파일 업로드를 위해 DiskFileUpload 클래스 생성.
        DiskFileUpload upload = new DiskFileUpload();

        // 업로드 할 파일의 최대 크기, 메모리상에 저장할 최대 크기, 업로드된 파일을 임시로 저장할 경로를 작성.
        upload.setSizeMax(1000000);
        upload.setSizeThreshold(4096);
        upload.setRepositoryPath(path);

        // 폼 페이지에서 전송된 요청 파라미터를 전달받도록 DiskFileUpload 객체 타입의 parseRequest()매서드를 작성.
        List items = upload.parseRequest(req);

        // 폼 페이지에서 전송된 요청 파라미터를 Iterator 클래스로 변환.
        Iterator params = items.iterator();

        while (params.hasNext()) { // 폼 페이지에서 전송된 요청 파라미터가 없을 때까지 반복하도록 Iterator 객체 타입의 hasNext() 메서드를 작성.
            // 폼 페이지에서 전송된 요청 파라미터의 이름을 가져오도록 Iterator 객체 타입의 next() 메서드를 작성.
            FileItem item = (FileItem) params.next();

            if (item.isFormField()) {
                // 폼 페이지에서 전송된 요청 파라미터가 일반 테이터이면 요청 파라미터의 이름과 값을 출력.
                String name = item.getFieldName();
                String value = item.getString("utf-8");

                switch (name) {
                    case "name":
                        board.setName(value);
                    case "subject":
                        board.setSubject(value);
                        break;
                    case "content":
                        board.setContent(value);
                        break;

                }

                System.out.println(name + "=" + value + "<br>");
            } else {
                // 폼 페이지에서 전송된 요청 파라미터가 파일이면
                // 요청 파라미터의 이름, 저장 파일의 이름, 파일 컨텐트 유형, 파일 크기에 대한 정보를 출력.
                String fileFieldName = item.getFieldName();
                String fileName = item.getName();
                String contentType = item.getContentType();


                if (!fileName.isEmpty()) {
                    System.out.println("파일 이름 :" + fileName);
                    fileName = nowStr + fileName.substring(fileName.lastIndexOf("\\") + 1);
                    long fileSize = item.getSize();

                    File file = new File(path + "/" + fileName);
                    item.write(file);

                    board.setFilename(fileName);
                    board.setFilesize(fileSize);

                    System.out.println("---------------------------<br>");
                    System.out.println("요청 파라미터 이름 : " + fileFieldName + "<br>");
                    System.out.println("저장 파일 이름 : " + fileName + "<br>");
                    System.out.println("파일 콘텐츠 타입 : " + contentType + "<br>");
                    System.out.println("파일크기 :" + fileSize);
                }

            }
        }

        board.setIp(req.getRemoteAddr());

        dao.insertBoard(board);

    }

    // 선택된 글 상세 페이지 가져오기
    public void requestBoardView(HttpServletRequest req) {
        BoardDAO dao = BoardDAO.getInstance();
        int num = Integer.parseInt(req.getParameter("num"));
        int pageNum = Integer.parseInt(req.getParameter("pageNum"));

        BoardDTO board = new BoardDTO();
        board = dao.getBoardByNum(num, pageNum);
        System.out.println(num);
        req.setAttribute("num", num);
        req.setAttribute("page", pageNum);
        req.setAttribute("board", board);

    }

    // 선택된 글 내용 수정하기
    public void requestBoardUpdate(HttpServletRequest req) throws Exception {

        int num = Integer.parseInt(req.getParameter("num"));
        int pageNum = Integer.parseInt(req.getParameter("pageNum"));

        BoardDAO dao = BoardDAO.getInstance();

        dao.getFileName(num);
        System.out.println("getfileName() : " + dao.getFileName(num));

        BoardDTO board = new BoardDTO();


        LocalDateTime now = LocalDateTime.now();
        String nowStr = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));


        board.setNum(num);
        board.setName(req.getParameter("name"));
        board.setSubject(req.getParameter("subject"));
        board.setContent(req.getParameter("content"));


        // 폼 페이지에서 전송된 파일을 저장할 서버의 경로를 작성.
        String path = "C:\\imgs";

        // 파일 업로드를 위해 DiskFileUpload 클래스 생성.
        DiskFileUpload upload = new DiskFileUpload();

        // 업로드 할 파일의 최대 크기, 메모리상에 저장할 최대 크기, 업로드된 파일을 임시로 저장할 경로를 작성.
        upload.setSizeMax(1000000);
        upload.setSizeThreshold(4096);
        upload.setRepositoryPath(path);

        // 폼 페이지에서 전송된 요청 파라미터를 전달받도록 DiskFileUpload 객체 타입의 parseRequest()매서드를 작성.
        List items = upload.parseRequest(req);


        // 폼 페이지에서 전송된 요청 파라미터를 Iterator 클래스로 변환.
        Iterator params = items.iterator();

        while (params.hasNext()) { // 폼 페이지에서 전송된 요청 파라미터가 없을 때까지 반복하도록 Iterator 객체 타입의 hasNext() 메서드를 작성.
            // 폼 페이지에서 전송된 요청 파라미터의 이름을 가져오도록 Iterator 객체 타입의 next() 메서드를 작성.
            FileItem item = (FileItem) params.next();

            if (item.isFormField()) {
                // 폼 페이지에서 전송된 요청 파라미터가 일반 테이터이면 요청 파라미터의 이름과 값을 출력.
                String name = item.getFieldName();
                String value = item.getString("utf-8");


                switch (name) {
                    case "name":
                        board.setName(value);
                    case "subject":
                        board.setSubject(value);
                        break;
                    case "chkdID":
                        if (value == null || value.equals("")) {
                            System.out.println("체크 안됨");
                        } else {
                            System.out.println("체크 됨");
                            dao.deleteImg(num);
                            board.setFilename(null);
                            System.out.println(value);
                        }
                        break;
                    case "content":
                        board.setContent(value);
                        break;
                }


                System.out.println(name + "=" + value + "<br>");
            } else {
                // 폼 페이지에서 전송된 요청 파라미터가 파일이면
                // 요청 파라미터의 이름, 저장 파일의 이름, 파일 컨텐트 유형, 파일 크기에 대한 정보를 출력.
                String fileFieldName = item.getFieldName();
                String fileName = item.getName();
                String contentType = item.getContentType();
                long fileSize = 0;

                if (fileName.isEmpty()) { //파일이 비었을 때 - 파일 그대로
                    fileName = dao.getFileName(num);
                    board.setFilename(fileName);

                    System.out.println("--------------확인-------------<br>");
                    System.out.println("요청 파라미터 이름 : " + fileFieldName + "<br>");
                    System.out.println("저장 파일 이름 : " + fileName + "<br>");
                    System.out.println("파일 콘텐츠 타입 : " + contentType + "<br>");
                    System.out.println("파일크기 :" + fileSize);
                } else { //파일 변경 했을 때
                    fileName = nowStr + item.getName();
                    System.out.println("파일 이름 :" + fileName);
                    fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
                    fileSize = item.getSize();

                    File file = new File(path + "/" + fileName);
                    item.write(file);

                    board.setFilename(fileName);
                    board.setFilesize(fileSize);

                    System.out.println("---------------------------<br>");
                    System.out.println("요청 파라미터 이름 : " + fileFieldName + "<br>");
                    System.out.println("저장 파일 이름 : " + fileName + "<br>");
                    System.out.println("파일 콘텐츠 타입 : " + contentType + "<br>");
                    System.out.println("파일크기 :" + fileSize);
                }

            }

        }

        dao.updateBoard(board);
    }

    // 선택된 글 삭제하기
    public void requestBoardDelete(HttpServletRequest req) {

        int num = Integer.parseInt(req.getParameter("num"));
        int pageNum = Integer.parseInt(req.getParameter("pageNum"));

        BoardDAO dao = BoardDAO.getInstance();
        dao.deleteBoard(num);
    }


    // 댓글 등록
    public void requestRippleWrite(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        RippleDAO dao = RippleDAO.getInstance();
        RippleDTO ripple = new RippleDTO();
        HttpSession session = req.getSession();

        req.setCharacterEncoding("utf-8");

        ripple.setBoardName(req.getParameter("boardName"));
        ripple.setBoardNum(Integer.parseInt(req.getParameter("num")));
        ripple.setMemberId((String) session.getAttribute("sessionId"));
        ripple.setName(req.getParameter("name"));
        ripple.setContent(req.getParameter("content"));
        ripple.setIp(req.getRemoteAddr());

        String result = "{\"result\" : ";
        if (dao.insertRipple(ripple)) {
            result += "\"true\"}";
        } else {
            result += "\"false\"}";
        }
        // 결과 화면을 출력 스트림을 통해 출력
        PrintWriter out = resp.getWriter();
        out.append(result);
    }

    // 댓글 삭제
    public void requestRippleDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int rippleId = Integer.parseInt(req.getParameter("rippleId"));
        RippleDAO dao = RippleDAO.getInstance();
        RippleDTO ripple = new RippleDTO();
        ripple.setRippleId(rippleId);

        String result = "{\"result\" : ";
        if (dao.deleteRipple(ripple)) {
            result += "\"true\"}";
        } else {
            result += "\"false\"}";
        }
        // 결과 화면을 출력 스트림을 통해 출력
        PrintWriter out = resp.getWriter();
        out.append(result);
    }

    public void requestRippleList(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();
        String sessionMemberId = (String) session.getAttribute("sessionId");

        String boardName = req.getParameter("boardName");
        int num = Integer.parseInt(req.getParameter("num"));

        RippleDAO dao = RippleDAO.getInstance();
        ArrayList<RippleDTO> list = dao.getRippleList(boardName, num);

        StringBuilder result = new StringBuilder("{ \"listData\" : [");
        int i = 0;
        for (RippleDTO dto : list) {
            boolean flag = sessionMemberId != null && sessionMemberId.equals(dto.getMemberId()) ? true : false;
            result.append("{\"rippleId\" : \"").append(dto.getRippleId()).append("\", \"name\" : \"")
                    .append(dto.getName()).append("\", \"content\" : [\"").append(dto.getContent())
                    .append("\"], \"isWriter\":\"").append(flag).append("\"}");
            // value가 배열 형태로 들어가서 마지막 요소의 경우에는 콤마가 나오면 안됨.

            if (i++ < list.size() - 1)
                result.append(", ");
        }
        result.append("]}");

        // 결과 화면을 출력 스트림을 통해 출력
        PrintWriter out = resp.getWriter();
        out.append(result.toString());
    }

    //신고하기
    public void requestReportWrite(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        ReportDAO dao = ReportDAO.getInstance();
        ReportDTO report = new ReportDTO();
        HttpSession session = req.getSession();

        req.setCharacterEncoding("utf-8");

        report.setBoardNum(Integer.parseInt(req.getParameter("num")));
        report.setMemberId((String) session.getAttribute("sessionId"));
        report.setSubject(req.getParameter("subject"));
        report.setReportContent(req.getParameter("report"));


        String result = "{\"result\" : ";
        if (dao.insertReport(report)) {
            result += "\"true\"}";
        } else {
            result += "\"false\"}";
        }

    }


}
