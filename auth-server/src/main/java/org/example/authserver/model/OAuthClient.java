package org.example.authserver.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

//@Entity
@Table(name = "oauth_clients")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class OAuthClient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String scopes;
    private String grantTypes;

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof OAuthClient other)) return false;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$clientId = this.getClientId();
        final Object other$clientId = other.getClientId();
        if (this$clientId == null ? other$clientId != null : !this$clientId.equals(other$clientId)) return false;
        final Object this$clientSecret = this.getClientSecret();
        final Object other$clientSecret = other.getClientSecret();
        if (this$clientSecret == null ? other$clientSecret != null : !this$clientSecret.equals(other$clientSecret))
            return false;
        final Object this$redirectUri = this.getRedirectUri();
        final Object other$redirectUri = other.getRedirectUri();
        if (this$redirectUri == null ? other$redirectUri != null : !this$redirectUri.equals(other$redirectUri))
            return false;
        final Object this$scopes = this.getScopes();
        final Object other$scopes = other.getScopes();
        if (this$scopes == null ? other$scopes != null : !this$scopes.equals(other$scopes)) return false;
        final Object this$grantTypes = this.getGrantTypes();
        final Object other$grantTypes = other.getGrantTypes();
        return this$grantTypes == null ? other$grantTypes == null : this$grantTypes.equals(other$grantTypes);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof OAuthClient;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $clientId = this.getClientId();
        result = result * PRIME + ($clientId == null ? 43 : $clientId.hashCode());
        final Object $clientSecret = this.getClientSecret();
        result = result * PRIME + ($clientSecret == null ? 43 : $clientSecret.hashCode());
        final Object $redirectUri = this.getRedirectUri();
        result = result * PRIME + ($redirectUri == null ? 43 : $redirectUri.hashCode());
        final Object $scopes = this.getScopes();
        result = result * PRIME + ($scopes == null ? 43 : $scopes.hashCode());
        final Object $grantTypes = this.getGrantTypes();
        result = result * PRIME + ($grantTypes == null ? 43 : $grantTypes.hashCode());
        return result;
    }

    public String toString() {
        return "OAuthClient(id=" + this.getId() + ", clientId=" + this.getClientId() + ", clientSecret=" + this.getClientSecret() + ", redirectUri=" + this.getRedirectUri() + ", scopes=" + this.getScopes() + ", grantTypes=" + this.getGrantTypes() + ")";
    }

    public Long getId() {
        return this.id;
    }

    public String getClientId() {
        return this.clientId;
    }

    public String getClientSecret() {
        return this.clientSecret;
    }

    public String getRedirectUri() {
        return this.redirectUri;
    }

    public String getScopes() {
        return this.scopes;
    }

    public String getGrantTypes() {
        return this.grantTypes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public void setScopes(String scopes) {
        this.scopes = scopes;
    }

    public void setGrantTypes(String grantTypes) {
        this.grantTypes = grantTypes;
    }
}
