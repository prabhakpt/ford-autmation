Run("C:\Program Files (x86)\RSA SecurID Software Token\SecurID.exe")
   WinWaitActive("Shiva - RSA SecurID Token")
   Send("1234")
   Sleep( 3000 )
   Send("{ENTER}")
   Sleep( 1000 )
   Send("+{TAB 3}");

   Sleep(1000 )
   Send("{ENTER}")
   Sleep( 1000 )
   Send("+{TAB 4}");
   Sleep( 1000 )
   Send("{ENTER}")
   Sleep( 1000 )
   Send("{CTRLDOWN}c{CTRLUP}")
   WinClose("Shiva - RSA SecurID Token")