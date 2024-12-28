document.addEventListener('DOMContentLoaded',function(){
  console.log('DOMContentLoaded');

  const userIdInput = document.querySelector("#userId");
  const deptNoInput = document.querySelector("#deptNo");
  const supUserIdInput = document.querySelector("#supUserId");
  const nameInput = document.querySelector("#name");
  const passwordInput = document.querySelector("#password");
  const positionInput = document.querySelector("#position");
  const birthdayInput = document.querySelector("#birthday");
  const phoneNoInput = document.querySelector("#phoneNo");
  const statusInput = document.querySelector("#status");  

  //목록으로 되돌아가기
  const moveToPreviousBtn = document.querySelector("#moveToPrevious");

  moveToPreviousBtn.addEventListener("click", function(event){
    event.preventDefault();
      console.log('moveToListButton click');

      if(confirm('회원 목록으로 이동하시겠습니까?') === false) return;

      //화면이니까 앞에 /ehr 더 붙여주기
      // /user는 원래 붙이는 건데 스프링에서만 생략
      // 그래서 포맷이 밑처럼 됨
      window.location.href = "/ehr/main/main.do"
  });


  //수정
  const doUpdateButton = document.querySelector("#doUpdate");
  doUpdateButton.addEventListener("click",function(event){
    event.preventDefault();

    //Validation
  if(isEmpty(deptNoInput.value) === true){
      alert('부서 번호를 입력하세요.')
      deptNoInput.focus();
      return;
  } 
  if(isEmpty(supUserIdInput.value) === true){
      alert('상급자 ID를 입력하세요.')
      supUserIdInput.focus();
      return;
  } 
  if(isEmpty(nameInput.value) === true){
      alert('이름을 입력하세요.')
      nameInput.focus();
      return;
  } 
  if(isEmpty(passwordInput.value) === true){
      alert('비밀번호를 입력하세요.')
      passwordInput.focus();
      return;
  } 
  if(isEmpty(positionInput.value) === true){
      alert('직급을 입력하세요.')
      positionInput.focus();
      return;
  } 
  if(isEmpty(birthdayInput.value) === true){
      alert('생년월일을 입력하세요.')
      birthdayInput.focus();
      return;
  } 
  if(isEmpty(phoneNoInput.value) === true){
      alert('연락처를 입력하세요.')
      phoneNoInput.focus();
      return;
  } 
  if(isEmpty(statusInput.value) === true){
      alert('상태값을 입력하세요.')
      statusInput.focus();
      return;
  } 

  if(confirm('회원을 수정하시겠습니까?') === false) return;

  $.ajax({
      type: "POST",
      url: "/ehr/user/doUpdate.do",
      async: true,
      dataType: "html", //form 보낼 땐 html 쓴다고 생각하면 됨
      data: {
          "userId"   : userIdInput.value,
          "deptNo"   : deptNoInput.value,
          "supUserId": supUserIdInput.value,
          "name"     : nameInput.value,
          "password" : passwordInput.value,
          "position" : positionInput.value,
          "birthday" : birthdayInput.value,
          "phoneNo"  : phoneNoInput.value,
          "status"   : statusInput.value
      },
      success: function(response) {
          console.log("success response:" + response);
          const message = JSON.parse(response);
          if( 1 ===message.messageId){ //수정 성공
              alert(message.message);
              //목록으로 화면 이동
              window.location.href = "/ehr/main/main.do";
          }else{ 
              alert(message.message);
          }
          
      },
      error: function(response) {
          console.log("error:" + response);
      }
  });

  });

});