package org.sakaiproject.profile2.tool.pages.panels;

import java.io.IOException;
import java.io.ObjectInputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.ResourceModel;
import org.sakaiproject.profile2.logic.SakaiProxy;
import org.sakaiproject.profile2.model.UserProfile;
import org.sakaiproject.profile2.tool.Locator;
import org.sakaiproject.profile2.util.ProfileUtils;

public class MyInterestsDisplay extends Panel {
	
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(MyInfoDisplay.class);
	private int visibleFieldCount = 0;
	private transient SakaiProxy sakaiProxy;
	
	public MyInterestsDisplay(final String id, final UserProfile userProfile) {
		super(id);
		
		//this panel stuff
		final Component thisPanel = this;
		
		//get API's
		sakaiProxy = getSakaiProxy();
		
		//get userProfile from userProfileModel
		//UserProfile userProfile = (UserProfile) this.getModelObject();
		
		//get info from userProfile since we need to validate it and turn things off if not set.
		//otherwise we could just use a propertymodel
		String favouriteBooks = userProfile.getFavouriteBooks();
		String favouriteTvShows = userProfile.getFavouriteTvShows();
		String favouriteMovies = userProfile.getFavouriteMovies();
		String favouriteQuotes = userProfile.getFavouriteQuotes();
		String otherInformation = ProfileUtils.unescapeHtml(userProfile.getOtherInformation());
		
		//heading
		add(new Label("heading", new ResourceModel("heading.interests")));
		
		//favourite books
		WebMarkupContainer booksContainer = new WebMarkupContainer("booksContainer");
		booksContainer.add(new Label("booksLabel", new ResourceModel("profile.favourite.books")));
		booksContainer.add(new Label("favouriteBooks", favouriteBooks));
		add(booksContainer);
		if(StringUtils.isBlank(favouriteBooks)) {
			booksContainer.setVisible(false);
		} else {
			visibleFieldCount++;
		}
		
		//favourite tv shows
		WebMarkupContainer tvContainer = new WebMarkupContainer("tvContainer");
		tvContainer.add(new Label("tvLabel", new ResourceModel("profile.favourite.tv")));
		tvContainer.add(new Label("favouriteTvShows", favouriteTvShows));
		add(tvContainer);
		if(StringUtils.isBlank(favouriteTvShows)) {
			tvContainer.setVisible(false);
		} else {
			visibleFieldCount++;
		}
		
		//favourite movies
		WebMarkupContainer moviesContainer = new WebMarkupContainer("moviesContainer");
		moviesContainer.add(new Label("moviesLabel", new ResourceModel("profile.favourite.movies")));
		moviesContainer.add(new Label("favouriteMovies", favouriteMovies));
		add(moviesContainer);
		if(StringUtils.isBlank(favouriteMovies)) {
			moviesContainer.setVisible(false);
		} else {
			visibleFieldCount++;
		}
		
		//favourite quotes
		WebMarkupContainer quotesContainer = new WebMarkupContainer("quotesContainer");
		quotesContainer.add(new Label("quotesLabel", new ResourceModel("profile.favourite.quotes")));
		quotesContainer.add(new Label("favouriteQuotes", favouriteQuotes));
		add(quotesContainer);
		if(StringUtils.isBlank(favouriteQuotes)) {
			quotesContainer.setVisible(false);
		} else {
			visibleFieldCount++;
		}
		
		//other info
		WebMarkupContainer otherContainer = new WebMarkupContainer("otherContainer");
		otherContainer.add(new Label("otherLabel", new ResourceModel("profile.other")));
		otherContainer.add(new Label("otherInformation", ProfileUtils.escapeHtmlForDisplay(otherInformation)).setEscapeModelStrings(false));
		add(otherContainer);
		if(StringUtils.isBlank(otherInformation)) {
			otherContainer.setVisible(false);
		} else {
			visibleFieldCount++;
		}
		
		
				
		//edit button
		AjaxFallbackLink editButton = new AjaxFallbackLink("editButton", new ResourceModel("button.edit")) {
			
			private static final long serialVersionUID = 1L;

			public void onClick(AjaxRequestTarget target) {
				Component newPanel = new MyInterestsEdit(id, userProfile);
				newPanel.setOutputMarkupId(true);
				thisPanel.replaceWith(newPanel);
				if(target != null) {
					target.addComponent(newPanel);
					//resize iframe
					target.appendJavascript("setMainFrameHeight(window.name);");
				}
				
			}
						
		};
		editButton.add(new Label("editButtonLabel", new ResourceModel("button.edit")));
		editButton.setOutputMarkupId(true);
		
		if(userProfile.isLocked() && !sakaiProxy.isSuperUser()) {
			editButton.setVisible(false);
		}
		
		add(editButton);
		
		//no fields message
		Label noFieldsMessage = new Label("noFieldsMessage", new ResourceModel("text.no.fields"));
		add(noFieldsMessage);
		if(visibleFieldCount > 0) {
			noFieldsMessage.setVisible(false);
		}
		
	}
	
	/* reinit for deserialisation (ie back button) */
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		log.debug("MyInterestsDisplay has been deserialized.");
		//re-init our transient objects
		sakaiProxy = getSakaiProxy();
	}

	
	private SakaiProxy getSakaiProxy() {
		return Locator.getSakaiProxy();
	}
	
}
