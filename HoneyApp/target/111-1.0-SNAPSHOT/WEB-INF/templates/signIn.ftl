<#import "spring.ftl" as spring />
<!DOCTYPE html>
<html lang="en" lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Sign In</title>
</head>
<body>
    <div class="main">
        <section class="sign-in">
            <div class="container">
                <div class="signin-content">
                    <div class="signin-form">
                        <h2 class="form-title"><@spring.message 'form.sign.in'/></h2>
                        <form method="POST" class="register-form" id="login-form">
                            <#if status??>
                                ${status}
                            </#if>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            <div class="form-group">
                                <label for="exampleInputEmail1">Email</label>
                                <input type="email" class="form-control" id="email" aria-describedby="emailHelp"
                                       placeholder="<@spring.message 'form.email'/>" name="email">
                            </div>
                            <div class="form-group">
                                <label for="exampleInputPassword1">Password</label>
                                <input type="password" class="form-control" id="password" placeholder="<@spring.message 'form.password'/>"
                                       name="password">
                            </div>
                            <div class="form-group">
                                <label>
                                    <input type="checkbox"
                                           name="remember-me"><@spring.message 'form.remember.me'/>
                                </label>
                            </div>
                            <div class="form-group form-button">
                                <input type="submit" name="signIn" id="signIn" class="form-submit" value="Log in"/>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>

    </div>
</body>