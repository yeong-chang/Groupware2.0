package com.pcwk.ehr.cmn;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.*;

public class StringUtil {
	static final Logger log = LogManager.getLogger(StringUtil.class);
	
	/**
	 * request에 파라미터 null처리
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static String nvl(String value, String defaultValue) {
		if (Strings.isNullOrEmpty(value)) {
			return defaultValue;
		}
		
		return value;
	}
	
	/**
	 * @param maxNum: 총 글수
	 * @param currentPageNo: 현재 페이지 번호
	 * @param rowPerPage: 페이지 사이즈(10, 20, ... 100)
	 * @param bottomCount: 10/5
	 * @param url: 서버 호출 URL
	 * @param scriptName: 자바스크립트 함수명
	 * @return "html 텍스트"
	 */
	public static String renderingPager(int maxNum, int currentPageNo, int rowPerPage, int bottomCount, String url, String scriptName) {
	    StringBuilder html = new StringBuilder(2000);

	    int maxPageNo = (maxNum - 1) / rowPerPage + 1; // 총 페이지 수
	    int startPageNo = ((currentPageNo - 1) / bottomCount) * bottomCount + 1; // 현재 블록의 시작 페이지
	    int endPageNo = Math.min(((currentPageNo - 1) / bottomCount + 1) * bottomCount, maxPageNo); // 현재 블록의 끝 페이지
	    long nowBlockNo = ((currentPageNo - 1) / bottomCount) + 1; // 현재 블록 번호
	    long maxBlockNo = ((maxNum - 1) / bottomCount) + 1; // 최대 블록 번호

	    if (currentPageNo > maxPageNo) {
	        return "";
	    }

	    html.append("<div class=\"pagination\">\n");

	    // « : 처음 페이지로 이동
	    if (nowBlockNo > 1) {
	        html.append("\t<a href=\"javascript:" + scriptName + "('" + url + "', 1);\" class=\"prev\"><i class=\"fa-solid fa-angles-left\"></i></a>\n");
	    }

	    // < : 이전 블록으로 이동
	    if (startPageNo > 1) {
	        html.append("\t<a href=\"javascript:" + scriptName + "('" + url + "', " + (startPageNo - bottomCount) + ");\">");
	        html.append("<span><i class=\"fa-solid fa-angle-left\"></i></span>");
	        html.append("</a>\n");
	    }

	    // 1 ~ N : 페이지 번호
	    for (int inx = startPageNo; inx <= endPageNo; inx++) {
	        if (inx == currentPageNo) {
	            html.append("\t<a href=\"#\" class=\"disabled\">");
	            html.append(inx);
	            html.append("</a>\n");
	        } else {
	            html.append("\t<a href=\"javascript:" + scriptName + "('" + url + "', " + inx + ");\" class=\"active\">");
	            html.append(inx);
	            html.append("</a>\n");
	        }
	    }

	    // > : 다음 블록으로 이동
	    if (endPageNo < maxPageNo) {
	        html.append("\t<a href=\"javascript:" + scriptName + "('" + url + "', " + (endPageNo + 1) + ");\">");
	        html.append("<span><i class=\"fa-solid fa-angle-right\"></i></span>");
	        html.append("</a>\n");
	    } else {
	        html.append("");
	    }

	    // » : 마지막 페이지로 이동
	    if (currentPageNo < maxPageNo) {
	        html.append("\t<a href=\"javascript:" + scriptName + "('" + url + "', " + maxPageNo + ");\">");
	        html.append("<span><i class=\"fa-solid fa-angles-right\"></i></span>");
	        html.append("</a>\n");
	    } else {
	        html.append("");
	    }

	    html.append("</div>");

	    log.debug(html.toString());

	    return html.toString();
	}
}