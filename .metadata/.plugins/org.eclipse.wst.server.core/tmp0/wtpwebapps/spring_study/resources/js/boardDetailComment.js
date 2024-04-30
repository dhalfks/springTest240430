console.log("boardDetailComment in");
console.log(bnoVal);

document.getElementById('cmtAddBtn').addEventListener('click',()=>{
    const cmtText = document.getElementById('cmtText').value;
    const cmtWriter = document.getElementById('cmtWriter').innerText;
    if(cmtText == null || cmtText == ''){
        alert("댓글을 입력해주세요.");
        document.getElementById('cmtText').focus();
        return false;
    }else{
        let cmtData={
            bno : bnoVal,
            writer : cmtWriter,
            content : cmtText
        }
        console.log(cmtData);
        postCommentToServer(cmtData).then(result => {
            console.log(result);
            if(result == '1'){
                alert('댓글 등록 성공!!');
                document.getElementById('cmtText').value="";
                //화면에 뿌리기
                spreadCommentList(bnoVal);
            }
        })
    }

})


//비동기 통신 restful
//post : 데이터 객체를 컨트롤러로 보낼때 (삽입)
//get : 조회 (생략가능)
//put(patch) : 수정
//delete : 삭제
async function postCommentToServer(cmtData){
    try {
        const url = "/comment/post";
        const config={
            method:"post",
            headers:{
                "content-type":"application/json; charset=utf-8"
            },
            body:JSON.stringify(cmtData)
        };

        const resp = await fetch(url, config);
        const result = await resp.text();  // isOk
        return result;
    } catch (error) {
        console.log(error);
    }
}

//댓글 뿌리기
function spreadCommentList(bno){
    getCommentListFromServer(bno).then(result =>{
        console.log(result);
        const div = document.getElementById('accordionExample');
        if(result.length > 0){
            div.innerHTML =""; // 반복 전 기존 샘플 버리기
            //반복
            for(let i=0; i<result.length; i++){
                let add = `<div class="accordion-item">`;
                add += `<h2 class="accordion-header">`;
                add += `<button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapse${i}" aria-expanded="true" aria-controls="collapse${i}">`;
                add += `no.${result[i].cno} / ${result[i].writer} / ${result[i].reg_date}`;
                add += `</button> </h2>`;
                add += `<div id="collapse${i}" class="accordion-collapse collapse show" data-bs-parent="#accordionExample">`;
                add += `<div class="accordion-body">`;
                if(id === result[i].writer){
                    add += `<button type="button" data-cno="${result[i].cno}" class="btn btn-outline-warning btn-sm cmtModBtn">%</button>`;
                    add += `<button type="button" data-cno="${result[i].cno}" class="btn btn-outline-danger btn-sm cmtDelBtn">x</button>`
                }
                add += `<input type="text" class="form-control cmtText" value="${result[i].content}">`;
                add += `</div></div></div>`;
                div.innerHTML += add;
            }   
        }else{
            div.innerHTML = `<div class="accordion-body"> Comment List Empty </div>`;
        }
    })
}

document.addEventListener('click',(e)=>{
    console.log(e.target);
    if(e.target.classList.contains('cmtModBtn')){
        let cnoVal = e.target.dataset.cno;
        let div = e.target.closest('div');
        let cmtText = div.querySelector(".cmtText").value;
        let cmtData={
            cno : cnoVal,
            content : cmtText
        };
        //update 호출
        updateCommentToServer(cmtData).then(result =>{
            if(result == '1'){
                alert('댓글 수정 성공!!');
                spreadCommentList(bnoVal);
            }
        })
    }
    if(e.target.classList.contains('cmtDelBtn')){
        let cnoVal = e.target.dataset.cno;
        removeCommentFromServer(cnoVal, bnoVal).then(result =>{
            if(result == '1'){
                alert('댓글 삭제 성공!!');
                spreadCommentList(bnoVal);
            }
        })
    }
})

async function getCommentListFromServer(bno){
    try {
        // /comment/306
        const resp = await fetch("/comment/"+bno);
        const result = await resp.json();
        return result;
    } catch (error) {
        console.log(error);
    }
}


async function updateCommentToServer(cmtData){
    try {
        const url = "/comment/modify";
        const config={
            method:"put",
            headers:{
                'content-type':'application/json; charset=utf-8'
            },
            body: JSON.stringify(cmtData)
        };

        const resp = await fetch(url, config);
        const result = resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}

async function removeCommentFromServer(cno, bno){
    try {
        const url = "/comment/"+cno+"/"+bno;
        const config={
            method:"delete"
        }
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}