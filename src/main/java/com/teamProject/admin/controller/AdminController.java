package com.teamProject.admin.controller;

import com.teamProject.admin.model.*;

import com.teamProject.admin.model.ProductsDAO;
import com.teamProject.admin.model.ProductsDTO;
import com.teamProject.board.model.BoardDAO;
import com.teamProject.board.model.BoardDTO;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@WebServlet("*.ad")
public class AdminController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp); // get으로 넘어온 것을 post로 넘김.
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String RequestURI = req.getRequestURI(); // 전체 경로를 가져옴.
        String contextPath = req.getContextPath(); // 프로젝트 Path만 가져옴.
        String command =  RequestURI.substring(contextPath.length()); // 전체경로에서 프로젝트 Path 길이 만큼의 인덱스 이후의 문자열을 가져옴.

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");


        // 상품 관리
        if (command.contains("/AdminProductsList.ad")) { // 등록된 상품 리스트 출력하기
            requestProductList(req);
            RequestDispatcher rd = req.getRequestDispatcher("../admin/adminProducts.jsp");
            rd.forward(req, resp);
        }
        else if (command.contains("/AddProduct.ad")) {
            try {
                requestAddProduct(req);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            RequestDispatcher rd = req.getRequestDispatcher("../admin/AdminProductsList.ad");
            rd.forward(req, resp);
        }
        else if (command.contains("editProduct.ad")) { // 글 수정폼 출력
            requestProductView(req);
            RequestDispatcher rd = req.getRequestDispatcher("../admin/editProduct.jsp");
            rd.forward(req, resp);
        }
        else if (command.contains("processEditProduct.ad")) { // 수정 디비 업데이트
            try {
                requestProductUpdate(req);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            RequestDispatcher rd = req.getRequestDispatcher("../admin/AdminProductsList.ad");
            rd.forward(req, resp);
        }
        else if (command.contains("deleteProduct.ad")) {
            requestDeleteProduct(req);
            RequestDispatcher rd = req.getRequestDispatcher("../admin/AdminProductsList.ad");
            rd.forward(req, resp);
        } else if (command.contains("ajaxIdCheck.ad")) {
            PrintWriter out = resp.getWriter(); // out 객체
            String productId = req.getParameter("productId");
            System.out.println(productId);
            ProductsDAO productsDAO = ProductsDAO.getInstance();
            boolean result = productsDAO.checkProductId(productId);
            if (result == true) {
                out.print("true");
            } else {
                out.print("false");
            }
        }


        // 회원 관리
        else if (command.contains("AdminMemberList.ad")) {
            requestMemberList(req);
            RequestDispatcher rd = req.getRequestDispatcher("../admin/adminMember.jsp");
            rd.forward(req, resp);
        }
        else if (command.contains("updateState.ad")) {
            requestUpdateUserState(req);
            RequestDispatcher rd = req.getRequestDispatcher("../admin/AdminMemberList.ad");
            rd.forward(req, resp);
        }


        // 주문 관리
        else if (command.contains("adminOrderManagement.ad")) {
            requestOrderList(req);
            RequestDispatcher rd = req.getRequestDispatcher("../admin/adminOrder.jsp");
            rd.forward(req, resp);
        }
        else if (command.contains("shippingDone.ad")) {
            requestShippinDone(req);
            RequestDispatcher rd = req.getRequestDispatcher("../admin/adminOrderManagement.ad");
            rd.forward(req, resp);
        }
        
        else if (command.contains("/productsList.ad")) { // !!!!!제품 목록 리스트 추가
            productList(req);
            RequestDispatcher rd = req.getRequestDispatcher("../shop_db/products.jsp");
            rd.forward(req, resp);
        }
        
        else if (command.contains("/productView.ad")) { // !!!!!제품 목록 리스트 추가
            productView(req);
            RequestDispatcher rd = req.getRequestDispatcher("../shop_db/product.jsp");
            rd.forward(req, resp);
        }
    }



	private void requestShippinDone(HttpServletRequest req) {
        String orderNum = req.getParameter("orderNum");
        OrderDataDAO orderDataDAO = OrderDataDAO.getInstance();
        List<ProductsDTO> productsDTOList = new ArrayList<ProductsDTO>();
        productsDTOList = orderDataDAO.getProductDTOListByOrderNum(orderNum);
        ProductsDAO productsDAO = ProductsDAO.getInstance();

        productsDAO.updateCnt(productsDTOList);
        // 배송상태 업데이트도 해야됨 shippingProgress
        OrderInfoDAO orderInfoDAO = OrderInfoDAO.getInstance();
        orderInfoDAO.updateOrderStep(orderNum);

    }

    private void requestOrderList(HttpServletRequest req) {
        OrderInfoDAO orderInfoDAO = OrderInfoDAO.getInstance();
        OrderDataDAO orderDataDAO = OrderDataDAO.getInstance();
        List<OrderMergeDTO> orderMergeDTOList = new ArrayList<OrderMergeDTO>();
        ArrayList<String> orderNum = orderInfoDAO.getOrderNumByOrderStep();
        List<OrderInfoDTO> orderInfoList = new ArrayList<OrderInfoDTO>();


        for (String item : orderNum) {
            OrderMergeDTO orderMergeDTO = new OrderMergeDTO();
            List<OrderDataDTO> orderDataList = new ArrayList<OrderDataDTO>();
            OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
//            orderInfoList = orderInfoDAO.getOrderInfoListByOrderNum(item);
            orderInfoDTO = orderInfoDAO.getInfoDtoByOrderNum(item);
            orderDataList = orderDataDAO.getOrderDataListByOrderNum(item);
            orderMergeDTO.setOrderInfoDTO(orderInfoDTO);
            orderMergeDTO.setList(orderDataList);

            orderMergeDTOList.add(orderMergeDTO);
        }
//        req.setAttribute("orderInfoList", orderInfoList);
//        req.setAttribute("orderDataList", orderDataList);
        req.setAttribute("orderMergeDTOList", orderMergeDTOList);
    }


    private void requestMemberList(HttpServletRequest req) {
        MemberDAO memberDAO = MemberDAO.getInstance();
        List<MemberDTO> memberList = new ArrayList<MemberDTO>();

        int pageNum = 1; // 페이지 번호가 전달이 안되면 1페이지.
        int limit = 10; // 페이지당 게시물 수.

        if (req.getParameter("pageNum") != null) { // 페이지 번호가 전달이 된 경우.
            pageNum = Integer.parseInt(req.getParameter("pageNum"));
        }

        String items = req.getParameter("items"); // 검색 필드.
        String text = req.getParameter("text"); // 검색어.

        int total_record = memberDAO.getListCount(items, text); // 전체 게시물 수.
        memberList = memberDAO.getMemberList(pageNum, limit, items, text); // 현재 페이지에 해당하는 목록 데이터 가져오기.

        int total_page; // 전체 페이지

        if (total_record % limit == 0) { //  전체 게시물이 limit의 배수일 때.
            total_page = total_record / limit;
//            Math.floor(total_page);
        }
        else {
//            total_page = total_record / limit;
            total_page = (total_record / limit) + 1;
//            Math.floor(total_page);
//            total_page = total_page + 1;
        }

        int block = 5;
        int block_total = total_page % block == 0 ? total_page / block : total_page / block + 1; // 총 블럭의 수
        int block_this = ((pageNum - 1) / block) + 1; // 현재 페이지의 블럭
        int block_this_first_page = ((block_this - 1) * block) + 1; // 현재 블럭의 첫 페이지
        int block_this_last_page = block_this * block; // 현재 블럭의 마지막 페이지
        block_this_last_page = (block_this_last_page > total_page) ? total_page : block_this_last_page; // 마지막 블럭의 경우 전체 페이지 번호의 마지막 페이지

        req.setAttribute("limit", limit);
        req.setAttribute("pageNum", pageNum); // 페이지 번호.
        req.setAttribute("total_page", total_page); // 전체 페이지 수.
        req.setAttribute("total_record", total_record); // 전체 게시물 수.
        req.setAttribute("memberList", memberList); // 현재 페이지에 해당하는 목록 데이터.
        req.setAttribute("block_this", block_this); // 현재 페이지에 해당하는 목록 데이터.
        req.setAttribute("block_this_first_page", block_this_first_page); // 현재 페이지에 해당하는 목록 데이터.
        req.setAttribute("block_this_last_page", block_this_last_page); // 현재 페이지에 해당하는 목록 데이터.
        req.setAttribute("block_total", block_total); // 현재 페이지에 해당하는 목록 데이터.
        req.setAttribute("items", items); // 현재 페이지에 해당하는 목록 데이터.
        req.setAttribute("text", text); // 현재 페이지에 해당하는 목록 데이터.
    }

    private void requestUpdateUserState(HttpServletRequest req) {
        MemberDAO memberDAO = MemberDAO.getInstance();
        memberDAO.updateUserState(req);

    }

    private void requestDeleteProduct(HttpServletRequest req) {
        String productId = req.getParameter("productId");
        ProductsDAO productsDAO = ProductsDAO.getInstance();
        productsDAO.deleteProduct(productId);
    }

    private void requestProductUpdate(HttpServletRequest req) throws Exception  {
        ProductsDTO productsDTO = new ProductsDTO();
        ProductsDAO productsDAO = ProductsDAO.getInstance();

        // 폼 페이지에서 전송된 파일을 저장할 서버의 경로를 작성.
        String path = "/Users/kihaeng/study/teamProject/src/main/webapp/resources/images"; // 상대 경로 (아파치 bin 폴더에 upload 폴더가 만들어짐)

        // 파일 업로드를 위해 DiskFileUpload 클래스를 생성.
        DiskFileUpload upload = new DiskFileUpload();

        // 업로드할 파일의 최대 크기, 메모리상에 저장할 최대 크기, 업로드된 파일을 임시로 저장할 경로를 작성.
        upload.setSizeMax(1000000);
        upload.setSizeThreshold(4096);
        upload.setRepositoryPath(path);

        // 폼 페이지에서 전송된 요청 파라미터를 전달받도록 DiskFileUpload 객체 타입의 parseRequest() 메서드를 작성.
        List items = upload.parseRequest(req);

        // 폼 페이지에서 전송된 요청 파라미터를 Iterator 클래스로 변환.
        Iterator params = items.iterator();

        while (params.hasNext()) { // 폼 페이지에서 전송된 요청 파라미터가 없을 때까지 반복하도록 Iterator 객체 타입의 hasNext() 메서드를 작성.
            // 폼 페이지에서 전송된 요청 파라미터의 이름을 가져오도록 Iterator 객체 타입의 next() 메서드를 작성.
            FileItem item = (FileItem) params.next();

            if (item.isFormField()) {
                // 폼 페이지에서 전송된 요청 파라미터가 일반 데이터이면 요청 파라미터의 이름과 값을 출력.
                String name = item.getFieldName();
                String value = item.getString("utf-8");
                System.out.println(value);

                switch (name) {
                    case "productName":
                        productsDTO.setProductName(value);
                        break;
                    case "description":
                        productsDTO.setDescription(value);
                        break;
                    case "category":
                        productsDTO.setCategory(value);
                        break;
                    case "productId":
                        productsDTO.setProductId(value);
                        break;
                    case "productPrice":
                        productsDTO.setProductPrice(Integer.parseInt(value));
                        break;
                    case "productsInStock":
                        productsDTO.setProductsInStock(Integer.parseInt(value));
                        break;
                    case "fileName":
                        productsDTO.setFileName(value);
                        break;
                }
                System.out.println(name + "=" + value + "<br>");
            }
            else {
                // 폼 페이지에서 전송된 요청 파라미터가 파일이면
                // 요청 파라미터의 이름, 저장 파일의 이름, 파일 컨텐트 유형, 파일 크기에 대한 정보를 출력.
                String fileFieldName = item.getFieldName();
                String fileName = item.getName();
                String contentType = item.getContentType();

                if(!fileName.isEmpty()){
                    System.out.println("파일 이름 : " + fileName);
                    fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
                    if (new File(path + "/" + fileName).exists()) {
                        Date date = new Date();
                        SimpleDateFormat today = new SimpleDateFormat("yyyyMMddHHmmss");
                        String date1 = today.format(date);
                        int num = fileName.indexOf(".");
                        fileName = fileName.substring(0, num) + date1 + "." + fileName.substring(num + 1);
                    }

                    long fileSize = item.getSize();

                    File file = new File(path + "/" + fileName);
                    item.write(file);

                    productsDTO.setFileName(fileName);
//                    productsDTO.setFilesize(fileSize);

                    System.out.println("----------------------------------<br>");
                    System.out.println("요청 파라미터 이름 : " + fileFieldName  + "<br>");
                    System.out.println("저장 파일 이름 : " + fileName + "<br>");
                    System.out.println("파일 콘텐츠 타입 : " + contentType + "<br>");
                    System.out.println("파일 크기 : " + fileSize);
                }
            }
        }
        productsDAO.updateProduct(productsDTO, req);
    }


    // 선택된 상픔 상세 정보 가져오기
    private void requestProductView(HttpServletRequest req) {
        ProductsDAO productsDAO = ProductsDAO.getInstance();
        String productId = req.getParameter("productId");
        int pageNum = Integer.parseInt(req.getParameter("pageNum"));
        String items = req.getParameter("items");
        String text = req.getParameter("text");

        ProductsDTO productsDTO = new ProductsDTO();
        productsDTO = productsDAO.getBoardByProductId(productId);

        req.setAttribute("productId", productId);
        req.setAttribute("page", pageNum);
        req.setAttribute("productsDTO", productsDTO);
        req.setAttribute("items", items);
        req.setAttribute("text", text);
    }

    private void requestProductList(HttpServletRequest req) {
        ProductsDAO productsDAO = ProductsDAO.getInstance();
        List<ProductsDTO> productList = new ArrayList<ProductsDTO>();

        int pageNum = 1; // 페이지 번호가 전달이 안되면 1페이지.
        int limit = 10; // 페이지당 게시물 수.

        if (req.getParameter("pageNum") != null) { // 페이지 번호가 전달이 된 경우.
            pageNum = Integer.parseInt(req.getParameter("pageNum"));
        }

        String items = req.getParameter("items"); // 검색 필드.
        String text = req.getParameter("text"); // 검색어.

        int total_record = productsDAO.getListCount(items, text); // 전체 게시물 수.
        productList = productsDAO.getProductList(pageNum, limit, items, text); // 현재 페이지에 해당하는 목록 데이터 가져오기.

        int total_page; // 전체 페이지

        if (total_record % limit == 0) { //  전체 게시물이 limit의 배수일 때.
            total_page = total_record / limit;
//            Math.floor(total_page);
        }
        else {
//            total_page = total_record / limit;
            total_page = (total_record / limit) + 1;
//            Math.floor(total_page);
//            total_page = total_page + 1;
        }

        int block = 5;
        int block_total = total_page % block == 0 ? total_page / block : total_page / block + 1; // 총 블럭의 수
        int block_this = ((pageNum - 1) / block) + 1; // 현재 페이지의 블럭
        int block_this_first_page = ((block_this - 1) * block) + 1; // 현재 블럭의 첫 페이지
        int block_this_last_page = block_this * block; // 현재 블럭의 마지막 페이지
        block_this_last_page = (block_this_last_page > total_page) ? total_page : block_this_last_page; // 마지막 블럭의 경우 전체 페이지 번호의 마지막 페이지

        req.setAttribute("limit", limit);
        req.setAttribute("pageNum", pageNum); // 페이지 번호.
        req.setAttribute("total_page", total_page); // 전체 페이지 수.
        req.setAttribute("total_record", total_record); // 전체 게시물 수.
        req.setAttribute("productList", productList); // 현재 페이지에 해당하는 목록 데이터.
        req.setAttribute("block_this", block_this); // 현재 페이지에 해당하는 목록 데이터.
        req.setAttribute("block_this_first_page", block_this_first_page); // 현재 페이지에 해당하는 목록 데이터.
        req.setAttribute("block_this_last_page", block_this_last_page); // 현재 페이지에 해당하는 목록 데이터.
        req.setAttribute("block_total", block_total); // 현재 페이지에 해당하는 목록 데이터.
        req.setAttribute("items", items); // 현재 페이지에 해당하는 목록 데이터.
        req.setAttribute("text", text); // 현재 페이지에 해당하는 목록 데이터.



    }

    private void requestAddProduct(HttpServletRequest req) throws Exception {
        ProductsDTO productsDTO = new ProductsDTO();
        ProductsDAO productsDAO = ProductsDAO.getInstance();

        // 폼 페이지에서 전송된 파일을 저장할 서버의 경로를 작성.
        String path = "C:\\Users\\CHJ\\eclipse-workspace\\teamProject\\src\\main\\webapp\\resources\\images";

        // 파일 업로드를 위해 DiskFileUpload 클래스를 생성.
        DiskFileUpload upload = new DiskFileUpload();

        // 업로드할 파일의 최대 크기, 메모리상에 저장할 최대 크기, 업로드된 파일을 임시로 저장할 경로를 작성.
        upload.setSizeMax(1000000);
        upload.setSizeThreshold(4096);
        upload.setRepositoryPath(path);

        // 폼 페이지에서 전송된 요청 파라미터를 전달받도록 DiskFileUpload 객체 타입의 parseRequest() 메서드를 작성.
        List items = upload.parseRequest(req);

        // 폼 페이지에서 전송된 요청 파라미터를 Iterator 클래스로 변환.
        Iterator params = items.iterator();

        while (params.hasNext()) { // 폼 페이지에서 전송된 요청 파라미터가 없을 때까지 반복하도록 Iterator 객체 타입의 hasNext() 메서드를 작성.
            // 폼 페이지에서 전송된 요청 파라미터의 이름을 가져오도록 Iterator 객체 타입의 next() 메서드를 작성.
            FileItem item = (FileItem) params.next();

            if (item.isFormField()) {
                // 폼 페이지에서 전송된 요청 파라미터가 일반 데이터이면 요청 파라미터의 이름과 값을 출력.
                String name = item.getFieldName();
                String value = item.getString("utf-8");
                System.out.println(value);

                switch (name) {
                    case "productName":
                        productsDTO.setProductName(value);
                        break;
                    case "description":
                        productsDTO.setDescription(value);
                        break;
                    case "category":
                        productsDTO.setCategory(value);
                        break;
                    case "productId":
                        productsDTO.setProductId(value);
                        break;
                    case "productPrice":
                        productsDTO.setProductPrice(Integer.parseInt(value));
                        break;
                    case "productsInStock":
                        productsDTO.setProductsInStock(Integer.parseInt(value));
                        break;
                }
                System.out.println(name + "=" + value + "<br>");
            }
            else {
                // 폼 페이지에서 전송된 요청 파라미터가 파일이면
                // 요청 파라미터의 이름, 저장 파일의 이름, 파일 컨텐트 유형, 파일 크기에 대한 정보를 출력.
                String fileFieldName = item.getFieldName();
                String fileName = item.getName();
                String contentType = item.getContentType();

                if(!fileName.isEmpty()) {
                    System.out.println("파일 이름 : " + fileName);
                    fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);

                    if (new File(path + "/" + fileName).exists()) {
                        Date date = new Date();
                        SimpleDateFormat today = new SimpleDateFormat("yyyyMMddHHmmss");
                        String date1 = today.format(date);
                        int num = fileName.indexOf(".");
                        fileName = fileName.substring(0, num) + date1 + "." + fileName.substring(num + 1);
                    }

                    long fileSize = item.getSize();

                    File file = new File(path + "/" + fileName);
                    item.write(file);

                    productsDTO.setFileName(fileName);
//                    productsDTO.setFilesize(fileSize);

                    System.out.println("----------------------------------<br>");
                    System.out.println("요청 파라미터 이름 : " + fileFieldName  + "<br>");
                    System.out.println("저장 파일 이름 : " + fileName + "<br>");
                    System.out.println("파일 콘텐츠 타입 : " + contentType + "<br>");
                    System.out.println("파일 크기 : " + fileSize);
                }

            }
        }
        productsDAO.insertProduct(productsDTO);
    }
    
    
    
    
    private void productList(HttpServletRequest req) {
        ProductsDAO productsDAO = ProductsDAO.getInstance();
        List<ProductsDTO> productList = new ArrayList<ProductsDTO>();

        int pageNum = 1;
        int limit = 6;

        if (req.getParameter("pageNum") != null)
            pageNum = Integer.parseInt(req.getParameter("pageNum"));

        String items = req.getParameter("items");
        String text = req.getParameter("text");

        int total_record = productsDAO.getListCount(items, text);
        productList = productsDAO.getProductList(pageNum, limit, items, text);
        int total_page;

        if (total_record % limit == 0) {
            total_page = total_record / limit;
            Math.floor(total_page);
        }
        else {
            total_page = total_record / limit;
            Math.floor(total_page);
            total_page = total_page + 1;
        }

        req.setAttribute("limit", limit);
        req.setAttribute("pageNum", pageNum);
        req.setAttribute("total_page", total_page);
        req.setAttribute("total_record", total_record);
        req.setAttribute("boardlist", productList);
    }
    
    
    private void productView(HttpServletRequest req) {
        ProductsDAO dao = ProductsDAO.getInstance();
        String productId = req.getParameter("productId");
        int num = Integer.parseInt(req.getParameter("num"));
        int pageNum = Integer.parseInt(req.getParameter("pageNum"));

        ProductsDTO productsDTO = new ProductsDTO();
        productsDTO = dao.getBoardByNum(productId, pageNum);
        System.out.println(num);
        req.setAttribute("num", num);
        req.setAttribute("productId", productId);
        req.setAttribute("page", pageNum);
        req.setAttribute("productsDTO", productsDTO);
		
	}
}
