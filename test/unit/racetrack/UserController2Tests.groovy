package racetrack
 

import com.racetrack.security.codecs.SHACodec
import org.codehaus.groovy.grails.plugins.codecs.*
import org.junit.*
import grails.test.*

class UserController2Tests extends ControllerUnitTestCase
{
	protected void setUp() {
		super.setUp()
		String.metaClass.encodeAsBase64 = {->
			Base64Codec.encode(delegate)}
		String.metaClass.encodeAsSHA = {->
			SHACodec.encode(delegate)}
	}
	void testShow(){
		def jdoe = new User(login:"jdoe")
		def suziq = new User(login:"suziq")
		mockDomain(User, [jdoe, suziq])
		controller.params.id = 2
		def map = controller.show()
		assertEquals "suziq", map.userInstance.login
	}

	void testAuthenticate(){
		def jdoe = new User(login:"jdoe", password:"password".encodeAsSHA())
		mockDomain(User, [jdoe])
		controller.params.login = "jdoe"
		controller.params.password = "password"
		controller.authenticate()
		assertNotNull controller.session.user
		assertEquals "jdoe", controller.session.user.login
		controller.params.password = "foo"
		controller.authenticate()
		assertTrue controller.flash.message.startsWith("Sorry, jdoe")
	}
}
