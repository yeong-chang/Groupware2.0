 document.addEventListener('DOMContentLoaded',function(){
    console.log('DOMContentLoaded');

    const doSaveButton = document.querySelector('#doSave');

    const deptNoInput = document.querySelector("#deptNo");
    const nameInput = document.querySelector("#name");
    const passwordInput = document.querySelector("#password");
    const birthdayInput = document.querySelector("#birthday");
    const phoneNoInput = document.querySelector("#phoneNo");

    //등록
    doSaveButton.addEventListener('click', function(event){
        event.preventDefault();
        console.log('doSaveButton click');
        
        if(isEmpty(deptNoInput.value) === true){
            alert('부서 번호를 입력하세요.')
            deptNoInput.focus();
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

        if(confirm('회원 등록 하시겠습니까?') === false)return;

        $.ajax({
            type: "POST",
            url: "/ehr/user/doSave.do",
            async: true,
            dataType: "html", //form 보낼 땐 html 쓴다고 생각하면 됨
            data: {
                "deptNo"     : deptNoInput.value, 
                "name"    : nameInput.value,
                "password": passwordInput.value,
                "birthday"    : birthdayInput.value,
                "phoneNo"    : phoneNoInput.value
                
            },
            success: function(response) {
                console.log("success response:" + response);
                const message = JSON.parse(response);
                if( 1 ===message.messageId){ //등록 성공
                    if(confirm('회원 등록 성공! 로그인 창으로 이동하시겠습니까?\n신입사원일 경우, 관리자나 부서장에게 로그인 정보를 문의하세요.') === true){
                        //로그인 창으로 화면 이동
                        window.location.href = "/ehr/login/login_index.do";
                    } else{
                        window.location.href = "/ehr/main/main.do";
                    }
                    
                }else{ 
                    alert(message.message);
                }
              
            },
            error: function(response) {
                console.log("error:" + response);
            }
        });

    });
    
    //돌아가기 버튼(로그인으로 돌아감)
    const moveToPreviousButton = document.querySelector("#moveToPrevious");
        console.log(moveToPreviousButton);
    
    moveToPreviousButton.addEventListener("click", function(event){
        event.preventDefault();
        console.log('moveToPreviousButton click');

        if(confirm('로그인 창으로 이동하시겠습니까?') === true){
            //로그인 창으로 화면 이동
            window.location.href = "/ehr/login/login_index.do";
        } else{
            window.location.href = "/ehr/main/main.do";
        }
    });
});