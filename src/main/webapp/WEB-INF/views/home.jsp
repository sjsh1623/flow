<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>


<html>
<head>
    <title>임석현, 과제</title>

    <!-- index.css 주입-->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/index.css"/>

    <!-- UIkit 주입 -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/uikit@3.5.4/dist/css/uikit.min.css"/>

    <!-- UIkit 주입 -->
    <script src="https://cdn.jsdelivr.net/npm/uikit@3.5.4/dist/js/uikit.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/uikit@3.5.4/dist/js/uikit-icons.min.js"></script>
    \
    <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
    <script src="https://cdn.jsdelivr.net/npm/handlebars@latest/dist/handlebars.js"></script>
</head>
<body>
<div class="container">
    <!-- 상단 타이틀 -->
    <h2 class="bold">파일 확장자 차단</h2>
    <hr>
    <!-- 설명 -->
    <h4>파일확장자에 따라 특정 형식의 파일을 첨부하거나 전송하지 못하도록 제한합니다</h4>

    <!-- 확장자 추가 -->
    <form action="<%=request.getContextPath()%>/ext_select">
        <div>
            <div class="floatLeft bold head">기존 차단 확장자</div>
            <div class="floatLeft tail">
                <input onclick="preExistCheck()" type="checkbox" id="bat" name="preexist" value="bat">
                <label for="bat"> bat</label>
                <input onclick="preExistCheck()" type="checkbox" id="cmd" name="preexist" value="cmd">
                <label for="cmd"> cmd</label>
                <input onclick="preExistCheck()" type="checkbox" id="com" name="preexist" value="com">
                <label for="com"> com</label>
                <input onclick="preExistCheck()" type="checkbox" id="cpl" name="preexist" value="cpl">
                <label for="cpl"> cpl</label>
                <input onclick="preExistCheck()" type="checkbox" id="exe" name="preexist" value="exe">
                <label for="exe"> exe</label>
                <input type="checkbox" id="scr" name="preexist" value="scr">
                <label for="scr"> scr</label>
                <input type="checkbox" id="js" name="preexist" value="js">
                <label for="js"> js</label>
            </div>
        </div>

        <div class="clear push">
            <div class="floatLeft bold head">추가 차단 확장자</div>
            <div class="floatLeft tail">
                <div id="form-add-todo" class="form-add-todo">
                    <input type="text" id="new-todo-item"/>
                    <input type="button" id="add-todo-item" class="add" value="+추가" onclick="additionalCheck()"/>
                </div>

                <div class="box">
                    <div class="uk-card-body">
                        <div id="card" class="uk-child-width-1-6@m uk-grid-small uk-grid-match" uk-grid>

                            <!-- 핸들바를 사용하여 현재 데이터를 출력합니다 -->
                            <script type="text/x-handlebars-template" id="cardTemp">
                                {{#each additional}}
                                <div class="con">
                                    <div class="uk-card uk-card-default uk-card-small uk-card-body displayGenreDel">
                                        {{ext}}
                                    </div>
                                </div>
                                {{/each}}
                            </script>

                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="floatLeft clear head">&nbsp;</div>
        <!-- 저장 버튼을 누르면 해당 API를 비동기화로 입력합니다 -->
        <div class="floatLeft push tail">
            <button style="background-color:skyblue; border:none" type="button" onclick="complete()">저장</button>
            <button onclick="location.href='<%=request.getContextPath()%>/ext_delete'" style="background-color:indianred; border:none" type="reset">삭제</button>
        </div>
    </form>
</div>

<script src="<%=request.getContextPath()%>/assets/js/index.js"></script>
<script>

    // 모든 데이터를 불러옵니다 (GET방식)
    // 경로: /ext_select
    $.get("${pageContext.request.contextPath}/ext_select", function (json) {
        var preExist = json.preExist; // 기존 차단 확장자
        var additional = json.additional; //추가 차단 확장자

        var source1 = $("#cardTemp").html();   // 템플릿 코드 가져오기
        var template1 = Handlebars.compile(source1);  // 템플릿 코드 컴파일
        var result1 = template1(json); // 템플릿 컴파일 결과물에 json 전달
        $("#card").html(result1); // 최종 결과물을 #list 요소에 추가한다.

        function checked(item) {
            var json = JSON.stringify(item); //JSON을 String으로 변환
            var Array = JSON.parse(json); //JSON을 Parse하여 Array형식으로 변환
            var element = Array.ext; // element 도출
            document.getElementById(element).checked = true;
        }

        preExist.forEach(checked)
    })


    // 경로: /ext_update
    function complete() {
        var addArray = new Array();
        var len = $('input[name="additional"]').length;
        //배열에 값 주입
        for (var i = 0; i < len; i++) {
            addArray[i] = $('input[name="additional"]').eq(i).val();
        }

        var additional = JSON.stringify(addArray);
        var additionalTrimL = additional.replace('[', '');
        var result = additionalTrimL.replace(']', '');

        //업데이트가 방식에 마추어 요청합니다
        $.get("${pageContext.request.contextPath}/ext_update", {
            preExist: preExistCheck(),
            addition: result
        });

        location.reload();
    }

    // 모든 추가 데이터 삭제
    function reset() {
        $.get("${pageContext.request.contextPath}/ext_delete");
        location.reload();
    }


</script>

</body>
</html>