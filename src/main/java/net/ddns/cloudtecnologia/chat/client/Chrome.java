package net.ddns.cloudtecnologia.chat.client;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@Configuration
public class Chrome {

    WebDriver chrome;
    String USER_PATCH;

    ChatGpt chat = new ChatGpt();

    private static final String URL_LOGIN = "https://web.whatsapp.com/";

    private static final String URL_CHROME_DRIVER = "C:\\Users\\thiago.melo\\Downloads\\chromedriver1.exe";
    private static final String NAVEGADOR = "webdriver.chrome.driver";

    private static final String USER_AGENT = "--user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36";
    private static final String MAZIMIZADO = "start-maximized";


    public void confiugurarChrome() {
        System.setProperty(NAVEGADOR, URL_CHROME_DRIVER);

        String user = System.getProperty("user.home");
        USER_PATCH = user.replace("\\", "/");
        String perfilChrome = "--user-data-dir=" + USER_PATCH + "/AppData/Local/Google/Chrome/User Data/Default";
        ChromeOptions options = new ChromeOptions();
        options.addArguments(USER_AGENT);
        options.addArguments(perfilChrome);
        options.addArguments(MAZIMIZADO);
        options.addArguments("--disable-extensions");

        chrome = new org.openqa.selenium.chrome.ChromeDriver(options);
        chrome.manage().timeouts().implicitlyWait(10, TimeUnit.MILLISECONDS);
    }

    public void logarWhatsApp() {
        chrome.get(URL_LOGIN);
    }

    public void processarMsg() {
        Scanner ler = new Scanner(System.in);
        System.out.println("Digite 'ok' e pressione 'Enter' para iniciar!");
        ler.next();
        while (true == true) {
            System.out.println("Processando mensagens...");
            WebElement chat = chrome.findElement(By.xpath("//*[@id=\"main\"]/div[2]/div/div[2]/div[3]"));
            try {
                List<String> mensagens = new ArrayList<>();
                boolean enviar;
                for (WebElement element : chat.findElements(By.tagName("div"))) {
                    String remetente = element.getAttribute("data-pre-plain-text");
                    String hora = remetente;
                    if (!element.getText().trim().equals("") &&
                            element != null && remetente != null) {

                        remetente = remetente.substring
                                        (remetente.indexOf("]") + 1, remetente.length())
                                .replace(":", "").trim();
                        String msg = element.findElement(By.className("_21Ahp")).getText();
                        hora = hora.substring(1, hora.indexOf(",")).trim();
                        //
                        mensagens.add(remetente + " | " + msg + " | " + hora);
                    }
                }
                String ultMsg = mensagens.get(mensagens.size() - 1).trim();
                System.out.println("Última mensagem: " + ultMsg);
                String objMsg[] = ultMsg.split("[|]", -1);
                if (objMsg[0].trim().equals("ME")) {
                    enviar = false;
                    System.out.println("Enviar Mensagem: " + enviar);
                } else {
                    enviar = true;
                    System.out.println("Enviar Mensagem: " + enviar);
                    enviarMensagem(objMsg[1].trim());
                }

            } catch (Exception e) {
                System.out.println("Erro ao obter mensagens do remetente: DIV ");
                System.out.println(e.getCause());
            }
            System.out.println("-----------------------------------------------------");
            aguardar(5);
        }

    }

    public void aguardar(int value) {
        try {
            Thread.sleep(value * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void enviarMensagem(String mensagem) {
        String caixaMsgTag = "//*[@id=\"main\"]/footer/div[1]/div/span[2]/div/div[2]/div[1]/div/div[1]/p";
        WebElement caixaMsg = chrome.findElement(By.xpath(caixaMsgTag));
        caixaMsg.click();
        aguardar(2);
        caixaMsg.sendKeys(chat.responderMsg(mensagem));
        aguardar(10);
        chrome.findElement(By.xpath("//*[@id=\"main\"]/footer/div[1]/div/span[2]/div/div[2]/div[2]/button")).click();
    }

}
