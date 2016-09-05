ARR=(q1 q2 q3 q4)

if echo "${ARR[@]}" | grep -w "q1" &>/dev/null;
then
	echo "Found"
fi