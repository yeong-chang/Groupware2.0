        var simplemde = new SimpleMDE({
            element: document.getElementById("content"), // 마크다운 에디터를 적용할 textarea 지정
            autoDownloadFontAwesome: true, // FontAwesome 아이콘 자동 다운로드
            spellChecker: false, // 맞춤법 검사 비활성화 (원하는 경우 true로 설정)
            status: false // 상태 바 비활성화
        });
        document.getElementById("showTextareaBtn").addEventListener("click", function() {
            var textarea = document.getElementById("contentTextArea");
            textarea.style.display = "block";  // 보이게 함
            textarea.setAttribute("required", "true");  // required 속성 추가
        });