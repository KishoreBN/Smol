package com.primeengineer.smol.util;

public class Constants {
    public static final String USER_CREATED = "User created successfully";
    public static final String ACCOUNT_EXISTS = "Account already exists with the provided email";
    public static final String URL_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static final String VERIFICATION_EMAIL_SENT = "Verfication Email sent to registered email.";
    public static final String PASSWORD_RESET = "Reset your SMOL password";
    public static final String OTP_EMAIL_TEMPLATE_PREFIX = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "  <meta charset=\"UTF-8\">\n" +
            "  <title>Reset your SMOL password</title>\n" +
            "  <style>\n" +
            "    body {\n" +
            "      font-family: \"Segoe UI\", Roboto, sans-serif;\n" +
            "      background-color: #f7f7f7;\n" +
            "      margin: 0;\n" +
            "      padding: 0;\n" +
            "    }\n" +
            "    .container {\n" +
            "      max-width: 500px;\n" +
            "      margin: 40px auto;\n" +
            "      background-color: #ffffff;\n" +
            "      border-radius: 8px;\n" +
            "      padding: 30px;\n" +
            "      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);\n" +
            "    }\n" +
            "    .header {\n" +
            "      text-align: center;\n" +
            "      color: #7c3aed;\n" +
            "      font-size: 24px;\n" +
            "      font-weight: 600;\n" +
            "      margin-bottom: 20px;\n" +
            "    }\n" +
            "    .content {\n" +
            "      font-size: 16px;\n" +
            "      color: #333333;\n" +
            "      line-height: 1.6;\n" +
            "    }\n" +
            "    .otp-box {\n" +
            "      text-align: center;\n" +
            "      margin: 30px 0;\n" +
            "      padding: 15px;\n" +
            "      background-color: #f3f0fc;\n" +
            "      border: 2px dashed #7c3aed;\n" +
            "      border-radius: 6px;\n" +
            "      font-size: 22px;\n" +
            "      font-weight: bold;\n" +
            "      color: #7c3aed;\n" +
            "      letter-spacing: 2px;\n" +
            "    }\n" +
            "    .footer {\n" +
            "      margin-top: 30px;\n" +
            "      font-size: 14px;\n" +
            "      color: #888888;\n" +
            "      text-align: center;\n" +
            "    }\n" +
            "    a {\n" +
            "      color: #7c3aed;\n" +
            "      text-decoration: none;\n" +
            "    }\n" +
            "  </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "  <div class=\"container\">\n" +
            "    <div class=\"header\">Reset your SMOL password</div>\n" +
            "    <div class=\"content\">\n" +
            "      Hey there,<br><br>\n" +
            "      We received a request to reset the password for your <strong>Smol</strong> account.<br>\n" +
            "      Use the OTP below to proceed with the password reset:\n" +
            "      <div class=\"otp-box\">";
    public static final String OTP_EMAIL_TEMPLATE_SUFFIX = "</div>\n" +
            "      This OTP is valid for <strong>10 minutes</strong> and can be used only once.<br><br>\n" +
            "      If you didn’t request this, you can safely ignore this message. Your account remains secure.\n" +
            "    </div>\n" +
            "    <div class=\"footer\">\n" +
            "      — Team Smol<br>\n" +
            "      <a href=\"https://smoltech.shop\" target=\"_blank\">smoltech.shop</a>\n" +
            "    </div>\n" +
            "  </div>\n" +
            "</body>\n" +
            "</html>";
}
