select *
from t_hrm_attendance_record a
where CONCAT(EXTRACT(year from a.date),'-',EXTRACT(month from a.date)) = '${chooseTime}' and a.jobNum='${jobNum}'
ORDER BY a.jobNum asc,a.date asc