
GET: http://localhost:9090/

POST: http://localhost:9090/uploadFile
Body+formData:	file : file
				userId : 3
				docType : Adhar

GET:  http://localhost:9090/downloadFile?userId=3&docType=Adhar