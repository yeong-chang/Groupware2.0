document.addEventListener("DOMContentLoaded",function() {
  console.log('DOMContentLoaded');

    const approval_doc_no_Input = document.querySelector("#approval_doc_no");
    const approval_user_id_Input = document.querySelector("#approval_user_id");
    const approval_doc_title_Input = document.querySelector("#approval_doc_title");
    const approval_doc_reg_date_Input = document.querySelector("#approval_doc_reg_date");
    const approval_doc_approved_date_Input = document.querySelector("#approval_doc_approved_date");
    const approval_doc_closing_date_Input = document.querySelector("#approval_doc_closing_date");
    const approval_status_Input = document.querySelector("#approval_status");
    const approval_contents_Input = document.querySelector("#approval_contents");

    //목록으로 돌아가기
    const moveToListBtn = document.querySelector("#moveToList");

    moveToListBtn.addEventListener("click",function(event){
        event.preventDefault();
      console.log('moveToListButton click');

      if(confirm('결재건 목록으로 이동하시겠습니까?') === false) return;

      window.location.href = "/ehr/approval/doRetrieve.do";
    });

    //수정
    const doUpdateBtn = document.querySelector("#doUpdate");
    doUpdateBtn.addEventListener("click", function(event){
        event.preventDefault();

      if(isEmpty(approval_doc_no_Input.value) === true){
        alert('결재건 번호를 입력하세요.')
        approval_doc_no_Input.focus();
        return;
    } 
    if(isEmpty(approval_user_id_Input.value) === true){
        alert('상신자ID를 입력하세요.')
        approval_user_id_Input.focus();
        return;
    } 
    if(isEmpty(approval_doc_title_Input.value) === true){
        alert('제목을 입력하세요.')
        approval_doc_title_Input.focus();
        return;
    } 
    if(isEmpty(approval_doc_reg_date_Input.value) === true){
        alert('상신일을 입력하세요.')
        approval_doc_reg_date_Input.focus();
        return;
    } 

    if(isEmpty(approval_doc_closing_date_Input.value) === true){
        alert('마감일을 입력하세요.')
        approval_doc_closing_date_Input.focus();
        return;
    } 
    if(isEmpty(approval_status_Input.value) === true){
        alert('결재상태를 입력하세요.')
        approval_status_Input.focus();
        return;
    } 
    if(isEmpty(approval_contents_Input.value) === true){
        alert('내용을 입력하세요.')
        approval_contents_Input.focus();
        return;
    } 

    if(confirm('결재건을 수정하시겠습니까?') === false) return;

    $.ajax({
      type: "POST",
      url: "/ehr/approval/doUpdate.do",
      async: true,
      dataType: "html", //form 보낼 땐 html 쓴다고 생각하면 됨
      data: {
          "approval_doc_no"            : approval_doc_no_Input.value,
          "approval_user_id"           : approval_user_id_Input.value,
          "approval_doc_title"         : approval_doc_title_Input.value,
          "approval_doc_reg_date"      : approval_doc_reg_date_Input.value,
          "approval_doc_approved_date" : approval_doc_approved_date_Input.value,
          "approval_doc_closing_date"  : approval_doc_closing_date_Input.value,
          "approval_status"            : approval_status_Input.value,
          "approval_contents"          : approval_contents_Input.value
      },
      success: function(response) {
          console.log("success response:" + response);
          const message = JSON.parse(response);
          if( 1 ===message.messageId){ //수정 성공
              alert('결재 문서 수정을 성공하였습니다!');
              //목록으로 화면 이동
              window.location.href = "/ehr/approval/doRetrieve.do";
          }else{ 
              alert(message.message);
          } 
      },
      error: function(response) {
          console.log("error:" + response);
      }
     });
   })

    //삭제
    const doDeleteButton = document.querySelector("#doDelete");
    doDeleteButton.addEventListener("click",function(event){
        event.preventDefault();
        console.log("doDeleteButton click");
        console.log("approval_doc_no.value:"+approval_doc_no_Input.value);
        
        if (confirm("결재건을 삭제하시겠습니까?") === false) return;
        $.ajax({
            type: "GET",
            url: "/ehr/approval/doDelete.do",
            async: true,
            dataType: "html",
            data: {
                "approval_doc_no": approval_doc_no_Input.value
            },
            success: function(response) {
                console.log("success response:" + response);
                const message = JSON.parse(response);

                if (1 == message.messageId) {
                    alert(message.message);
                    window.location.href = "/ehr/approval/doRetrieve.do";
                } else {
                    alert(message.message);
                }
            },
            error: function(response) {
                console.log("error:" + response);
            }
        });
    });

    //결재
    const doApproveButton = document.querySelector("#doApprove");
    doApproveButton.addEventListener("click",function(event){
        event.preventDefault();
        console.log("doApproveButton click");

        if(confirm("결재 처리하시겠습니까?") === false) return;

        $.ajax({
            type: "GET",
            url: "/ehr/approval/doApprove.do",
            async: true,
            dataType: "html",
            data: {
                "approval_doc_no": approval_doc_no_Input.value,
                "approval_user_id":approval_user_id_Input.value
            },
            success: function(response) {
                console.log("success response:" + response);
                const message = JSON.parse(response);

                if (1 == message.messageId) {
                    alert('문서를 결재했습니다.');
                    window.location.href = "/ehr/approval/doRetrieve.do";
                } else {
                    alert('문서 결재에 실패했습니다. 결재자가 아닙니다.');
                }
            },
            error: function(response) {
                console.log("error:" + response);
            }
        });
    });

    //반려
    const doRejectButton = document.querySelector("#doReject");
    doRejectButton.addEventListener("click",function(event){
        event.preventDefault();
        console.log("doRejectButton click");

        if(confirm("반려 처리하시겠습니까?") === false) return;

        $.ajax({
            type: "GET",
            url: "/ehr/approval/doReject.do",
            async: true,
            dataType: "html",
            data: {
                "approval_doc_no": approval_doc_no_Input.value,
                "approval_user_id":approval_user_id_Input.value
                
            },
            success: function(response) {
                console.log("success response:" + response);
                const message = JSON.parse(response);

                if (1 == message.messageId) {
                    alert('문서를 반려했습니다.');
                    window.location.href = "/ehr/approval/doRetrieve.do";
                } else {
                    alert('문서 반려에 실패했습니다. 결재자로 등록된 이만 반려할 수 있습니다.');
                }
            },
            error: function(response) {
                console.log("error:" + response);
            }
        });
    });
});