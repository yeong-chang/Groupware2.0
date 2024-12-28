document.addEventListener('DOMContentLoaded',function(){
    console.log('DOMContentLoaded');

    const doSaveButton = document.querySelector('#doSave');

    const approval_doc_title_Input = document.querySelector("#approval_doc_title");
    const approval_doc_closing_date_Input = document.querySelector("#approval_doc_closing_date");
    const approval_contents_Input = document.querySelector("#approval_contents");

    //등록
    doSaveButton.addEventListener('click',function(event){
        event.preventDefault();
        console.log('doSaveButton clcik');

        if(isEmpty(approval_doc_title_Input.value) === true){
            alert('제목을 입력하세요.');
            approval_doc_title_Input.focus();
            return;
        } 

        if(isEmpty(approval_doc_closing_date_Input.value) === true){
            alert('마감일을 설정하세요.');
            approval_doc_closing_date_Input.focus();
            return;
        } 
        if(isEmpty(approval_contents_Input.value) === true){
            alert('내용을 입력하세요');
            approval_contents_Input.focus();
            return;
        }

        if(confirm('결재건을 등록하시겠습니까?') === false) return;

        $.ajax({
            type: "POST",
            url: "/ehr/approval/doSavaApprovalDoc.do",
            async: true,
            dataType: "html", //form 보낼 땐 html 쓴다고 생각하면 됨
            data: {
                "approval_doc_title"         : approval_doc_title_Input.value,
                "approval_doc_closing_date"  : approval_doc_closing_date_Input.value,
                "approval_contents"          : approval_contents_Input.value
            },
            success: function(response) {
                console.log("success response:" + response);
                const message = JSON.parse(response);
                if( 1 ===message.messageId){ //등록 성공
                    alert("결재 문서를 등록했습니다!");
                    
                    //로그인 창으로 화면 이동
                    window.location.href = "/ehr/approval/doRetrieve.do";
                }else{ 
                    alert(message.message);
                }
              
            },
            error: function(response) {
                console.log("error:" + response);
            }
        });
    });

    const moveToPreviousButton = document.querySelector("#moveToPrevious");
    moveToPreviousButton.addEventListener("click", function(event){
        event.preventDefault();
        console.log('moveToListButton click');

        if(confirm('목록으로 돌아가시겠습니까?') === false) return;
        
        window.location.href = "/ehr/approval/doRetrieve1.do"
    })
});