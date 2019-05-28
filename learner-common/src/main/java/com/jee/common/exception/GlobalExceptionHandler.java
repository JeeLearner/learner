package com.jee.common.exception;

import com.jee.common.result.CodeMsg;
import com.jee.common.result.ResultInfo;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.dao.DataAccessException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(Exception.class)
	public ResultInfo<String> exceptionHandler(Exception e){
		e.printStackTrace();
		if(e instanceof GlobalException){
			GlobalException ex = (GlobalException)e;
			return ResultInfo.error(ex.getCodeMsg());
		} else if(e instanceof BindException){
			BindException ex = (BindException)e;
			List<ObjectError> allErrors = ex.getAllErrors();
			ObjectError error = allErrors.get(0);
			String msg = error.getDefaultMessage();
			return ResultInfo.error(CodeMsg.BIND_ERROR.fillArgs(msg));
		}else {
			return ResultInfo.error(CodeMsg.SERVER_ERROR);
		}
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResultInfo<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
		e.printStackTrace();
		BindingResult result = e.getBindingResult();
		if (result.hasErrors()){
			List<ObjectError> allErrors = result.getAllErrors();
			ObjectError error = allErrors.get(0);
			return ResultInfo.error(CodeMsg.BIND_ERROR.fillArgs(error.getDefaultMessage()));
		}
		return ResultInfo.error(CodeMsg.BIND_ERROR);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResultInfo<String> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e){
		e.printStackTrace();
		return ResultInfo.error(CodeMsg.REQUEST_TYPE_ERROR.fillArgs(e.getMessage()));
	}

	@ExceptionHandler(UnauthorizedException.class)
	public ResultInfo<String> unauthorizedExceptionHandler(UnauthorizedException e){
		e.printStackTrace();
		return ResultInfo.error(CodeMsg.PERMISSION_ERROR);
	}

	@ExceptionHandler(DataAccessException.class)
	public ResultInfo dataAccessException(DataAccessException e){
		e.printStackTrace();
		return ResultInfo.error(CodeMsg.SQL_DATA_ACCESS_ERROR);
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResultInfo handlerNoFoundException(NoHandlerFoundException e) {
		e.printStackTrace();
		return ResultInfo.error(CodeMsg.COMMON_NOFOUND_HANDLER);
	}

	@ExceptionHandler(AuthorizationException.class)
	public ResultInfo handleAuthorizationException(AuthorizationException e){
		e.printStackTrace();
		return ResultInfo.error(CodeMsg.PERMISSION_ERROR);
	}
}
