const inputId = document.querySelector("#productId");
inputId.addEventListener('keyup', function () {
    const xhr = new XMLHttpRequest();
    const productId = document.newProduct.productId.value; // 아이디 input에 있는 값.
    const productIdCheck = document.querySelector('.idCheck'); // 결과 문자열이 표현될 영역
    xhr.open('GET', 'ajaxIdCheck.ad?productId=' + productId); // HTTP 요청 초기화. 통신 방식과 url 설정.
    xhr.send(); // url에 요청을 보냄.
    // 이벤트 등록. XMLHttpRequest 객체의 readyState 프로퍼티 값이 변할 때마다 자동으로 호출
    xhr.onreadystatechange = () => {
        // readyState 프로퍼티의 값이 DONE : 요청한 데이터의 처리가 완료되어 응답할 준비가 완료됨.
        if (xhr.readyState !== XMLHttpRequest.DONE) return;

        if (xhr.status === 200) { // 서버(url)에 문서가 존재하면
            console.log(xhr.response);
            const result = xhr.response;
            if (result === 'false') {
                productIdCheck.style.color = "red";
                productIdCheck.innerHTML = "동일한 제품ID가 있습니다.";
            }
            else if (result === 'true') {
                productIdCheck.style.color = "green";
                productIdCheck.innerHTML = "동일한 제품ID가 없습니다.";
            }
            else {
                console.log("몬가.. 잘못됨...(귀엽 > ~ <)")
            }
        }
        else {   // 서버(url)에 문서가 존재X
            console.error('Error', xhr.status, xhr.statusText);
        }

    }
})