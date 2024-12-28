document.addEventListener('DOMContentLoaded',function(){
    console.log('DOMContentLoaded');

    const loginButton = document.querySelector("#loginBtn");
    const userIdInput = document.querySelector("#userId");
    const passwordInput = document.querySelector("#password");

    

    loginButton.addEventListener("click",function(event){
        event.preventDefault();
        console.log("loginButton click");

        //필수처리
        if( isEmpty(userIdInput.value) === true ){
            alert('사용자 ID를 입력 하세요.');
            userIdInput.focus();
            return;
          }  

        if( isEmpty(passwordInput.value) === true ){
        alert('비밀번호를 입력 하세요.');
        passwordInput.focus();
        return;
        }  

        $.ajax({
            type: "POST",
            url: "/ehr/login/login.do",
            async: true,
            dataType: "html",
            data: {
                "userId"   : userIdInput.value,
                "password" : passwordInput.value
            },
            success: function(response) {
                console.log("success response: " + response);
                const message = JSON.parse(response);

                if( 10 == message.messageId){ //ID 불일치
                    alert(message.message);
                    userIdInput.focus();
                    
                }else if(20 == message.messageId){ //비번 불일치
                    alert(message.message);
                    passwordInput.focus();
                    
                }else if (30 == message.messageId){ //로그인 완료
                    alert(message.message);
                    //main으로 이동
                    //url 이동
                    
                    window.location.href = "/ehr/main/main.do";
                }else{ //오류
                    alert(message.message);
                }
       
            },
            error: function(response) {
                console.log("error:" + response);
            }
          });
    });
    
    const memberRegButton = document.querySelector("#memberRegBtn");

    memberRegButton.addEventListener("click",function(event){
        event.preventDefault();
        console.log("memberRegButton click");

        window.location.href = "/ehr/user/user_reg_index.do";


    });
});