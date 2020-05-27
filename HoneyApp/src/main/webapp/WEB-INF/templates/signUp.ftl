<#import "spring.ftl" as spring />
<!DOCTYPE html>
<html lang="en" contentType="text/html; charset=UTF-8">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Sign Up</title>
    <html lang="en">
</head>
<body>
    <div class="main">
        <section class="signup">
            <div class="container">
                <div class="signup-content">
                    <div class="text-red">
                        <span><h1><@spring.message 'form.sign.up'/></h1></span>
                        <br>
                        <br>
                        <br>
                        <form method="POST" class="register-form" id="register-form">
                            <div class="form-group">
                                <label for="username"><@spring.message 'form.username'/></label><br/>
                                <@spring.formInput "signUpDto.username"/>
                                <@spring.showErrors "<br>"/>
                            </div>
                            <div class="form-group">
                                <label for="email"><@spring.message 'form.email'/></label><br/>
                                <@spring.formInput "signUpDto.email"/>
                                <@spring.showErrors "<br>"/>
                            </div>
                            <div class="form-group">
                                <label for="email"><@spring.message 'form.password'/></label><br/>
                                <@spring.formInput "signUpDto.password"/>
                                <@spring.showErrors "<br>"/>
                            </div>
                            <div class="form-group form-button">
                                <input type="submit" name="signup" id="signup" class="form-submit" value="<@spring.message 'form.sign.up.button'/>"/>
                            </div>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        </form>
                    </div>
                </div>
            </div>
        </section>
    </div>
</body>
<!doctype html>

