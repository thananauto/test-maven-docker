#!/bin/sh
#we have named the container ‘my-con’ in the above command so we are using that name in below #command, however, we can use contaienr ID but we need to get the id that required to write some #additional lines of code
exit_code="$(docker wait gorest_api)"
#Compare the status code
while [ $exit_code -eq "0" ];
do
    echo “Job has encountered some error.”
    echo container code :$exit_code
    exit_code="$(docker wait gorest_api)"
done

