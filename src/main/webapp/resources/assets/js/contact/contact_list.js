document.addEventListener('DOMContentLoaded',function(){
  console.log('DOMContentLoaded');

  const doRetrieveButton =document.querySelector("#doRetrieveBtn");
  console.log(doRetrieveButton);

  const pageNoInput     = document.querySelector("#pageNo");
  const searchDivSelect = document.querySelector("#searchDiv");
  const searchWordInput = document.querySelector("#searchWord");
  const pageSizeSelect  = document.querySelector("#pageSize");

  //모든 rows (데이터들)
  const rows = document.querySelectorAll("#listTable>tbody>tr");
  
  //각 row에 event 감지 및 처리
  rows.forEach(function(row){
      row.addEventListener('dblclick', function(event){
          event.preventDefault();
          let cells = row.getElementsByTagName("td");

          if(confirm('선택하신 회원에게 채팅하시겠습니까?') === false) return;

          //채팅으로 이어가는 href (채팅 구현 이후 수정하기)
          //window.location.href = "/ehr/user/doSelectOne.do?userId=" + userId;
      });
  })

  const userForm = document.querySelector("#userForm");
  doRetrieveButton.addEventListener('click',function(event){
      event.preventDefault(); //버블링 방지
      console.log("doRetrieveButton click");

      userForm.pageNo.value = 1;
      userForm.action = "/ehr/user/doRetrieve.do";

      console.log("pageSizeSelect.value: ", pageSizeSelect.value);
      console.log("searchDivSelect.value: ", searchDivSelect.value);
      console.log("searchWordInput.value: ", searchWordInput.value);
      console.log("pageNo.value: ", userForm.pageNo.value);

      userForm.submit();

  });
  
});