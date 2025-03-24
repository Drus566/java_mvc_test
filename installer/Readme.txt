* Формируем артефакт jsoftwaredb.jar
* Открываем lauch4j (скачать можно тут https://sourceforge.net/projects/launch4j/) и открываем конфигурацию config_launch4j.xml,
    Настройка:
     Вкладка Basic
       Output file: Путь к результирующему файлу, обязательно должно быть расширение .exe
       Jar: Путь к .jar файлу приложения
       Java download URL: путь к скачиванию java - https://sourceforge.net/projects/javaclientmsiinstallers/files/Java%208%20Update%20211/
     Вкладка Classpath
       Main class: главный класс приложения - ru.invertor.jsoftwaredb.Application
       Classpath: путь к хранимым классам - jsoftwaredb.jar
     Вкладка JRE:
       Min JRE Version: минимальная версия java - 1.8.0_0
       Max JRE Version: максимальная версия java - 1.8.0_401

     Далее жмем шестеренку в верхнем меню. После нажатия во вкладке Basic в поле Log должно появится сообщение 'Successfully created C:\Users\LykovA\Desktop\Workplace\Projects\jsoftwaredb\installer\jsoftwaredb\jsoftwaredb.exe', значит .exe файл успешно создан
* Если требуется преобразовать картинку в формат лого logo.bmp - https://online-converting.com/image/convert2bmp/

* Либо можем запускать с помощью jsoftwaredb.bat файла