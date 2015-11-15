package core;

import com.google.inject.Inject;
import play.Configuration;
import play.Logger;
import play.Play;
import play.libs.mailer.Email;
import play.libs.mailer.MailerClient;

/**
 * Allow to send emails.
 *
 * @author Sebastian Sachtleben
 */
public class MailService {

    private static final Logger.ALogger log = Logger.of(MailService.class);

    @Inject
    MailerClient mailerClient;

    @Inject
    Configuration configuration;

    public static MailService instance() {
        return Play.application().injector().instanceOf(MailService.class);
    }

    public void sendEmailConfirmation(final models.entity.Email email) {
        final Email emailObject = new Email()
            .setSubject("Email confirmation")
            .setFrom(configuration.getString("email.address"))
            .addTo(email.getAddress())
            .setBodyHtml(views.html.emails.emailconfirmation.render().body());
        mailerClient.send(emailObject);
    }

}
