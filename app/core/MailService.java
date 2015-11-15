package core;

import com.google.inject.Inject;
import play.Play;
import play.libs.mailer.Email;
import play.libs.mailer.MailerClient;

/**
 * Allow to send emails.
 *
 * @author Sebastian Sachtleben
 */
public class MailService {

    @Inject
    MailerClient mailerClient;

    public static MailService instance() {
        return Play.application().injector().instanceOf(MailService.class);
    }

    public void sendEmailConfirmation(final models.entity.Email email) {
        Email emailObject = new Email()
            .setSubject("Email confirmation")
            .setFrom("Mister FROM <from@email.com>")
            .addTo(email.getAddress())
            .setBodyText("Email confirmation")
            .setBodyHtml("<html><body><h1>Email confirmation</h1></body></html>");
        mailerClient.send(emailObject);
    }

}
