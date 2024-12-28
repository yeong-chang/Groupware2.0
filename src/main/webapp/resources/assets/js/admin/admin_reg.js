document.addEventListener('DOMContentLoaded',function(){
  console.log('DOMContentLoaded');

  const doSaveButton = document.querySelector('#doSave');

  const userIdInput = document.querySelector("#userId");
  const deptNoInput = document.querySelector("#deptNo");
  const supUserIdInput = document.querySelector("#supUserId");
  const nameInput = document.querySelector("#name");
  const passwordInput = document.querySelector("#password");
  const positionInput = document.querySelector("#position");
  const birthdayInput = document.querySelector("#birthday");
  const phoneNoInput = document.querySelector("#phoneNo");
  const statusInput = document.querySelector("#status");

  doSaveButton.addEventListener('click', function(event){
      event.preventDefault();
      console.log('doSaveButton click');

      //Validation
      if(isEmpty(userIdInput.value) === true){
          alert('사용자 ID를 입력하세요.')
          userIdInput.focus();
          return;
      } 

      if(isEmpty(deptNoInput.value) === true){
          alert('부서를 선택하세요.')
          deptNoInput.focus();
          return;
      } 

      if(isEmpty(supUserIdInput.value) === true){
          alert('상급자ID를 입력하세요.')
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
          alert('직급을 선택하세요.')
          positionInput.focus();
          return;
      } 
      if(isEmpty(birthdayInput.value) === true){
          alert('생년월일을 선택하세요.')
          birthdayInput.focus();
          return;
      } 
      if(isEmpty(phoneNoInput.value) === true){
          alert('연락처를 입력하세요.')
          phoneNoInput.focus();
          return;
      } 
      if(isEmpty(statusInput.value) === true){
          alert('상태를 입력하세요.')
          statusInput.focus();
          return;
      } 

      $.ajax({
          type: "POST",
          url: "/ehr/user/doSave.do",
          async: true,
          dataType: "html", //form 보낼 땐 html 쓴다고 생각하면 됨
          data: {
              "userId"     : userIdInput.value,
              "deptNo"     : deptNoInput.value,
              "supUserId"  : supUserIdInput.value,
              "name"       : nameInput.value,
              "password"   : passwordInput.value,
              "position"   : positionInput.value,
              "birthday"   : birthdayInput.value,
              "phoneNo"    : phoneNoInput.value,
              "status"     : statusInput.value
              
          },
          success: function(response) {
              console.log("success response:" + response);
              const message = JSON.parse(response);
              if( 1 ===message.messageId){ //등록 성공
                  alert("회원 등록을 성공했습니다!");
                  
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

  const moveToPreviousButton = document.querySelector("#moveToPrevious");
      console.log(moveToPreviousButton);
  
  moveToPreviousButton.addEventListener("click", function(event){
    event.preventDefault();
      console.log('moveToPreviousButton click');

      if(confirm('메인 페이지로 돌아가시겠습니까?') === false) return;

      //화면이니까 앞에 /ehr 더 붙여주기
      // /user는 원래 붙이는 건데 스프링에서만 생략
      // 그래서 포맷이 밑처럼 됨
      window.location.href = "/ehr/main/main.do"
  });
});