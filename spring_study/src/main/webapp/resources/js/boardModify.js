console.log("boardModify.js in");

document.addEventListener('click',(e)=>{
    if(e.target.classList.contains('file-x')){
        let uuid=e.target.dataset.uuid;
        let bno = e.target.dataset.bno;
        console.log(uuid);
        removeFileToServer(uuid, bno).then(result =>{
            if(result == 1){
                alert('파일 삭제!!');
                e.target.closest('li').remove();
                location.href="/board/modify?bno="+bno;
            }
        })
    }
});

//비동기 메서드 맵핑방법  : post, get, put, delete
async function removeFileToServer(uuid, bno){
    try {
        const url = "/board/"+uuid+"/"+bno;
        const config={
            method:'delete'
        }
        const resp = await fetch(url, config);
        const result = resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}