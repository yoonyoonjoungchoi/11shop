<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ page pageEncoding="EUC-KR"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
/***************************************************************
*	page �̵� ���� ���� ����
*	1. �� �������� �ִ� function fncGet~~~List() ���� ����
*	2. �ش� function�� ���� ������ ����� �����ϰ� �ֽ��ϴ�.
*		- �� list �������� <input type=hidden id=currentPage>
*		  �� value ����
*		- list ������ submit ����
*	3. ��� function�� ���� ����� �����Ѵٸ� ĸ��ȭ�� 
*	   �ϳ��� ����°� ���ڴٴ� ������ �����...
*	4. CommonScript.js�� function fncGetList(currentPage)
*		- ���� �� �� list �������� <script src="/javascript/CommonScript.js"></script>
*		import
*	5. pageNavigator.jsp�� fncGetList�� ȣ���ϵ��� ����
****************************************************************/


%>
<c:if test="${ resultPage.currentPage <= resultPage.pageUnit }">
		�� ����
</c:if>
<c:if test="${ resultPage.currentPage > resultPage.pageUnit }">
		<a href="javascript:fncGetList('${ resultPage.currentPage-1}')">�� ����</a>
</c:if>

<c:forEach var="i"  begin="${resultPage.beginUnitPage}" end="${resultPage.endUnitPage}" step="1">
	<a href="javascript:fncGetList('${ i }');">${ i }</a>
</c:forEach>

<c:if test="${ resultPage.endUnitPage >= resultPage.maxPage }">
		���� ��
</c:if>
<c:if test="${ resultPage.endUnitPage < resultPage.maxPage }">
		<a href="javascript:fncGetList('${resultPage.endUnitPage+1}')">���� ��</a>
</c:if>