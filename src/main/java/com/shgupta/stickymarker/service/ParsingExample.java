package com.shgupta.stickymarker.service;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.shgupta.stickymarker.domain.AccountHandler;
import com.shgupta.stickymarker.domain.User;

public class ParsingExample {

	public static void main(String[] args) {
		User user = new User("Sharad1", "Gupta", "sharad.gupta@hotmail.com");
		user.setUserId(1976L);
		AccountHandler<User> accountHandler = new AccountHandler<User>(user);
		EvaluationContext context = new StandardEvaluationContext();
		context.setVariable("user", user);
		
		ExpressionParser expressionParser = new SpelExpressionParser();
		//String value = expressionParser.parseExpression("T(java.lang.Math).random()").getValue(String.class);
		//System.out.println(value);
		//String value = expressionParser.parseExpression("T(com.shgupta.stickymarker.domain.User).formatName(#firstname)").getValue(context, String.class);
		
		System.out.println(expressionParser.parseExpression("#user.userId").getValue(context,String.class));

	}

}
