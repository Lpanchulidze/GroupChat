# GroupChat

````
* ვუშვებთ Server კლასს
* ვუშვებთ Client კლასს
* Client კლასის run/debug configuration -> Modify options -> Allow multiple instances
* Client კლასის თავიდან გაშვებით იმდენ კლიენტს ვამატებთ რამდენიც დაგვჭირდება
* კლიენტის დამატების დროს  აუცილებელია სახელის შეყვანა
* შეყვანის შემდეგ სერვერზე აისახება რომ ახალი კლიენტი შემოვიდა - A new client: has connected, ხოლო სერვერზე 
    მყოფ სხვა წევრებს მიუვათ შეტყობინება - SERVER: <user> has entered the chat! (გასვლის შემთხევაში -
    SERVER: <user> has left the chat!)
* კლიენტის ჩაწერილი ტექტსი ყველა სხვა აქტიურ კლიენტს გამოუჩნდება
* თუ კლიენტი ჩაწერს /commands სერვერი დაუბრუნებს აქტიური ბრძანებების ლისტს
* თუ კლიენტი ჩაწერს /time სერვერი დაუბრუნებს current დროს
* თუ კლიენტი ჩაწერს /help სერვერი ავტომატურად გაუხსნის საპორტის საიტს(ამ შემთხვევაში გუგლს)
* ზემოთ ჩამოთვლილი სამი command სხვა იუზერებს არ გამოუჩნდებათ!
````



შესაძლო პრობლემა სერვერის გაშვებისას:
````
Exception in thread "main" java.net.BindException: Address already in use: bind

Solution:
https://stackoverflow.com/questions/39632667/how-do-i-kill-the-process-currently-using-a-port-on-localhost-in-windows
````
