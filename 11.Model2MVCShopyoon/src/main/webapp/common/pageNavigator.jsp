<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ page pageEncoding="EUC-KR"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
/***************************************************************
*	page 이동 수정 내용 정리
*	1. 각 페이지에 있던 function fncGet~~~List() 전부 삭제
*	2. 해당 function은 전부 동일한 기능을 수행하고 있습니다.
*		- 각 list 페이지의 <input type=hidden id=currentPage>
*		  의 value 설정
*		- list 페이지 submit 실행
*	3. 모든 function이 같은 기능을 수행한다면 캡슐화로 
*	   하나로 만드는게 좋겠다는 생각이 들었다...
*	4. CommonScript.js에 function fncGetList(currentPage)
*		- 생성 후 각 list 페이지에 <script src="/javascript/CommonScript.js"></script>
*		import
*	5. pageNavigator.jsp도 fncGetList를 호출하도록 수정
****************************************************************/


%>
<c:if test="${ resultPage.currentPage <= resultPage.pageUnit }">
		◀ 이전
</c:if>
<c:if test="${ resultPage.currentPage > resultPage.pageUnit }">
		<a href="javascript:fncGetList('${ resultPage.currentPage-1}')">◀ 이전</a>
</c:if>

<c:forEach var="i"  begin="${resultPage.beginUnitPage}" end="${resultPage.endUnitPage}" step="1">
	<a href="javascript:fncGetList('${ i }');">${ i }</a>
</c:forEach>

<c:if test="${ resultPage.endUnitPage >= resultPage.maxPage }">
		이후 ▶
</c:if>
<c:if test="${ resultPage.endUnitPage < resultPage.maxPage }">
		<a href="javascript:fncGetList('${resultPage.endUnitPage+1}')">이후 ▶</a>
</c:if>