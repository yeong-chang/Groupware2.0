
    // 전역 변수로 selectedEvent 정의
    // 전역 변수로 selectedEvent 정의
    let selectedEvent = null;
    document.addEventListener('DOMContentLoaded', function () {
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
    googleCalendarApiKey: 'AIzaSyDKffPQQ1bZSeGnkwZ_rWbwH4bdVG6Cqfk',
    initialView: 'dayGridMonth',
    headerToolbar: {
    center: 'addEventButton'
},
    eventSources:[
{
    googleCalendarId : 'ko.south_korea#holiday@group.v.calendar.google.com',
    color: 'white',
    textColor: 'red',
    id: 'holidaySource'
}
    ],
    displayEventTime: false,
    eventBackgroundColor: '#990e17',
    selectable: true,
    select: function (info) {
    // 달력 셀 클릭 시 일정 추가 모달 열기
    var myModal = new bootstrap.Modal(document.getElementById('scheduleModal'));
    myModal.show();
    document.getElementById('startDate').value = info.startStr.split('T')[0];
    document.getElementById('endDate').value = info.endStr.split('T')[0];
},
    customButtons: {
    addEventButton: {
    text: '일정 추가',
    click: function () {
    var myModal = new bootstrap.Modal(document.getElementById('scheduleModal'));
    myModal.show();
}
}
},

    events: loadEvents, // 서버에서 데이터를 로드하는 함수 연결
    eventClick: function (info) {
    // 이벤트 클릭 시 수정/삭제 모달 열기
    selectedEvent = info.event;
    var editModal = new bootstrap.Modal(document.getElementById('editScheduleModal'));
    editModal.show();
    // 모달에 데이터 로드
    document.getElementById('editScheduleTitle').value = selectedEvent.title;
    document.getElementById('editScheduleContent').value = selectedEvent.extendedProps.content || '';
    document.getElementById('editStartDate').value = selectedEvent.startStr.split('T')[0];
    document.getElementById('editEndDate').value = selectedEvent.endStr
    ? selectedEvent.endStr.split('T')[0]
    : selectedEvent.startStr.split('T')[0];
}
});

    calendar.render();

    // 일정 저장 버튼 클릭
    document.getElementById('saveScheduleButton').addEventListener('click', async function () {
    var scheduleUserId = document.getElementById('scheduleUserId').value;
    var scheduleTitle = document.getElementById('scheduleTitle').value;
    var scheduleContent = document.getElementById('scheduleContent').value;
    var scheduleStartDate = document.getElementById('startDate').value;
    var scheduleEndDate = document.getElementById('endDate').value;

    var startDate = new Date(scheduleStartDate);
    var endDate = new Date(scheduleEndDate);

    // endDate의 시간을 23:59:59.999로 설정
    endDate.setHours(23, 59, 59, 999);

    // startDate와 endDate를 UTC로 변환
    var utcStartDate = new Date(Date.UTC(startDate.getFullYear(), startDate.getMonth(), startDate.getDate(), startDate.getHours(), startDate.getMinutes(), startDate.getSeconds(), startDate.getMilliseconds()));
    var utcEndDate = new Date(Date.UTC(endDate.getFullYear(), endDate.getMonth(), endDate.getDate(), endDate.getHours(), endDate.getMinutes(), endDate.getSeconds(), endDate.getMilliseconds()));

    if (!isNaN(utcStartDate.valueOf()) && !isNaN(utcEndDate.valueOf())) {
    if (utcStartDate <= utcEndDate) {
    calendar.addEvent({
    title: scheduleTitle,
    start: utcStartDate,
    end: utcEndDate,
    allDay: true,
    extendedProps: {
    content: scheduleContent,
    scheduleUserId: scheduleUserId
}
});

    try {
    const response = await fetch('addEvent.do', {
    method: 'POST',
    headers: {
    'Content-Type': 'application/json'
},
    body: JSON.stringify({
    scheduleUserId: scheduleUserId,
    scheduleTitle: scheduleTitle,
    scheduleContent: scheduleContent,
    scheduleStartDate: utcStartDate.toISOString(),
    scheduleEndDate: utcEndDate.toISOString()
})
});

    const data = await response.json();
    alert(data.message);
} catch (error) {
    console.error('Error:', error);
    alert('일정 등록에 실패했습니다.');
}
} else {
    alert('시작 날짜는 종료 날짜보다 늦을 수 없습니다.');
}
} else {
    alert('잘못된 날짜 형식입니다.');
}

    var myModal = bootstrap.Modal.getInstance(document.getElementById('scheduleModal'));
    myModal.hide();
});

    // 일정 삭제 버튼 클릭
    document.getElementById('deleteScheduleButton').addEventListener('click', async function () {
    if (!selectedEvent) {
    alert('삭제할 일정을 선택하지 않았습니다.');
    return;
}

    const scheduleId = selectedEvent.extendedProps && selectedEvent.extendedProps.scheduleId
    ? selectedEvent.extendedProps.scheduleId
    : selectedEvent.id; // id나 다른 속성도 확인 가능
    console.log("Deleting event with ID:", scheduleId); // 디버깅: scheduleId 확인
    console.log('selectedEvent:', selectedEvent); // selectedEvent 객체를 로그로 출력
    if (!scheduleId) {
    alert('일정 ID가 유효하지 않습니다.');
    return;
}

    if (confirm('일정을 삭제하시겠습니까?')) {
    try {
    const response = await fetch('deleteEvent.do', {
    method: 'POST',
    headers: {
    'Content-Type': 'application/json'
},
    body: JSON.stringify({ scheduleId })
});

    const data = await response.json();
    console.log("Server response:", data); // 디버깅: 서버 응답 확인
    if (data.message.includes('삭제')) {
    alert('일정이 삭제되었습니다.');
    selectedEvent.remove();
    selectedEvent = null; // 초기화
} else {
    alert('삭제 실패: ' + data.message);
}
} catch (error) {
    console.error('Error:', error);
    alert('서버 오류로 일정 삭제에 실패했습니다.');
}
}
});


    // 일정 수정 버튼 클릭
    document.getElementById('updateScheduleButton').addEventListener('click', async function () {
    if (!selectedEvent) {
    alert('수정할 일정을 선택하지 않았습니다.');
    return;
}

    const updatedTitle = document.getElementById('editScheduleTitle').value;
    const updatedContent = document.getElementById('editScheduleContent').value;
    const updatedStartDate = document.getElementById('editStartDate').value;
    const updatedEndDate = document.getElementById('editEndDate').value;

    // startDate와 endDate를 Date 객체로 변환
    var startDate = new Date(updatedStartDate);
    var endDate = new Date(updatedEndDate);

    // endDate의 시간을 11:59:59.999로 설정
    endDate.setHours(11, 59, 59, 999);

    // startDate와 endDate를 UTC로 변환
    var utcStartDate = new Date(Date.UTC(startDate.getFullYear(), startDate.getMonth(), startDate.getDate(), startDate.getHours(), startDate.getMinutes(), startDate.getSeconds(), startDate.getMilliseconds()));
    var utcEndDate = new Date(Date.UTC(endDate.getFullYear(), endDate.getMonth(), endDate.getDate(), endDate.getHours(), endDate.getMinutes(), endDate.getSeconds(), endDate.getMilliseconds()));

    try {
    const response = await fetch('updateEvent.do', {
    method: 'POST',
    headers: {
    'Content-Type': 'application/json'
},
    body: JSON.stringify({
    scheduleId: selectedEvent.id, // SCHEDULE_ID를 전송
    scheduleUserId: selectedEvent.extendedProps.scheduleUserId,
    scheduleTitle: updatedTitle,
    scheduleContent: updatedContent,
    // UTC로 변환된 날짜를 전송
    scheduleStartDate: utcStartDate.toISOString(),
    scheduleEndDate: utcEndDate.toISOString()
})
});

    const data = await response.json();
    if (data.success) {
    selectedEvent.setProp('title', updatedTitle);
    selectedEvent.setExtendedProp('content', updatedContent);
    selectedEvent.setStart(utcStartDate);
    selectedEvent.setEnd(utcEndDate);
    alert('일정이 성공적으로 수정되었습니다.');
} else {
    alert('수정 실패: ' + data.message);
}
} catch (error) {
    console.error('Error:', error);
    alert('수정 중 오류가 발생했습니다.');
}
});
    // 일정 완료 버튼 클릭
    document.getElementById('completeScheduleButton').addEventListener('click', async function () {
    if (!selectedEvent) {
    alert('완료할 일정을 선택하지 않았습니다.');
    return;
}

    const scheduleId = selectedEvent.extendedProps && selectedEvent.extendedProps.scheduleId
    ? selectedEvent.extendedProps.scheduleId
    : selectedEvent.id; // id나 다른 속성도 확인 가능;
    console.log(scheduleId);
    try {
    const response = await fetch('completeEvent.do', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ scheduleId })
});

    const data = await response.json();
    console.log(data);
    if (data.success) {
    alert('일정이 완료되었습니다.');
    // 캘린더 UI에서 색상 변경
    selectedEvent.setProp('backgroundColor', '#6c757d'); // 회색
    selectedEvent.setProp('borderColor', '#6c757d'); // 회색 테두리
} else {
    alert('완료 처리 실패: ' + data.message);
}
} catch (error) {
    console.error('Error:', error);
    alert('서버 오류로 일정 완료 처리에 실패했습니다.');
}

    // 모달 닫기
    const editModal = bootstrap.Modal.getInstance(document.getElementById('editScheduleModal'));
    editModal.hide();
});

});

    function loadEvents(fetchInfo, successCallback, failureCallback) {
    fetch('getEvents.do')
        .then(response => response.json())
        .then(data => {
            const events = data.map(event => ({
                id:event.id,
                title: event.title,
                start: event.start,
                end: event.end,
                backgroundColor: event.status === '20' ? '#6c757d' : '#990e17', // 완료 상태는 회색
                borderColor: event.status === '20' ? '#6c757d' : '#990e17',
                extendedProps: {
                    content: event.content,
                    scheduleUserId: event.scheduleUserId,
                    scheduleId: event.scheduleId,
                    status: event.status
                }
            }));
            successCallback(events);
        })
        .catch(error => {
            console.error('이벤트 로드 중 오류:', error);
            failureCallback(error);
        });
}